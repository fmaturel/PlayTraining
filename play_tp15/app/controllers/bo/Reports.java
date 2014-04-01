package controllers.bo;

import static play.libs.F.Promise.promise;
import models.Report;
import play.Logger;
import play.libs.F.Function;
import play.libs.F.Function0;
import play.libs.F.Promise;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import play.mvc.SimpleResult;
import views.html.bo.report;

import com.google.common.collect.Lists;

@Security.Authenticated(controllers.bo.Authenticator.class)
public class Reports extends Controller {

    public static Promise<Result> index() {
        long start = System.currentTimeMillis();
        Logger.info("Entre dans l'action de génération des rapports [{}]", start);

        final String username = request().username();

        Logger.info("Récupère le nom de l'utilisateur courant [{}] car il ne sera plus disponible plus tard", username);

        // Indicates that this report generation runs asynchronously
        final Promise<Report> promiseOfKPIReport = promise(new Function0<Report>() {
            @Override
            public Report apply() throws Throwable {
                return rapportStatistiquesVentes();
            }
        });

        final Promise<Report> promiseOfETAReport = promise(new Function0<Report>() {
            public Report apply() {
                return rapportStatistiquesExpeditions();
            }
        });

        // Indicates that we want results of report generation to be on the Promise object
        Promise<Result> result = promiseOfKPIReport.flatMap(new Function<Report, Promise<Result>>() {

            @Override
            public Promise<Result> apply(final Report r1) throws Throwable {
                return promiseOfETAReport.map(new Function<Report, Result>() {

                    @Override
                    public SimpleResult apply(final Report r2) {
                        return ok(report.render(Lists.newArrayList(r1, r2), username));
                    }
                });
            }
        });

        Logger.info("Termine l'action de génération des rapports [{}]", ((System.currentTimeMillis() - start) / 1000));

        return result;
    }

    public static Report rapportStatistiquesVentes() {
        Report r = new Report("Statistiques de ventes");
        r.execute();
        return r;
    }

    public static Report rapportStatistiquesExpeditions() {
        Report r = new Report("Statistiques d'expéditions");
        r.execute();
        return r;
    }

    @play.db.jpa.Transactional(readOnly = true)
    public static Result content(Long id) {
        final Report r = Report.findById(id);

        // Prepare a chunked text stream
        Chunks<String> chunks = new StringChunks() {
            // Called when the stream is ready
            public void onReady(Chunks.Out<String> out) {
                r.write(out);
                out.close();
            }
        };

        // Serves this stream with 200 OK
        return ok(chunks);
    }
}