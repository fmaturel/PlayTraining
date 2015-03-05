package controllers.bo;

import akka.actor.Cancellable;
import com.google.common.collect.Lists;
import models.Report;
import play.Logger;
import play.libs.Akka;
import play.libs.F.Function;
import play.libs.F.Function0;
import play.libs.F.Promise;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import scala.concurrent.duration.Duration;
import views.html.bo.report;

import java.util.concurrent.TimeUnit;

import static play.libs.F.Promise.promise;

@Security.Authenticated(controllers.bo.Authenticator.class)
public class Reports extends Controller {

    public static Promise<Result> index() {
        long start = System.currentTimeMillis();
        Logger.info("Entre dans l'action de génération des rapports [{}]", start);

        final String username = request().username();

        Logger.info("Récupère le nom de l'utilisateur courant [{}] car il ne sera plus disponible plus tard", username);

        // Indique que ce premier rapport est lancé de manière asynchrone
        final Promise<Report> promiseOfSellReport = promise(new Function0<Report>() {
            @Override
            public Report apply() throws Throwable {
                return rapportStatistiquesVentes();
            }
        });

        // Indique que ce deuxième rapport est lancé de manière asynchrone
        final Promise<Report> promiseOfExpeditionReport = promise(new Function0<Report>() {
            public Report apply() {
                return rapportStatistiquesExpeditions();
            }
        });

        // Indique que les résultats de la génération des rapports seront matérialisés par un objet Promise<T>
        Promise<Result> result = promiseOfSellReport.flatMap(new Function<Report, Promise<Result>>() {

            @Override
            public Promise<Result> apply(final Report r1) throws Throwable {
                return promiseOfExpeditionReport.map(new Function<Report, Result>() {

                    @Override
                    public Result apply(final Report r2) {
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

        // Prepare le streaming de type Chunked
        Chunks<String> chunks = new StringChunks() {
            // Quand le flux est prêt on peut écrire dedans de manière asynchrone
            public void onReady(Chunks.Out<String> out) {
                writeChunksAsync(out, r);
            }
        };

        // On retourne la réponse rapidement
        return ok(chunks);
    }

    static class Counter {
        public int count = 0;
    }

    private static void writeChunksAsync(final Chunks.Out<String> out, final Report r) {

        final Counter counter = new Counter();

        final Cancellable job = Akka.system().scheduler().schedule(//
                Duration.create(0, TimeUnit.MILLISECONDS),//
                Duration.create(1, TimeUnit.SECONDS), //
                new Runnable() {
                    @Override
                    public void run() {
                        String chunk = r.getChunk(counter.count++);
                        Logger.info("Ecrit le chunk [{}]", chunk);

                        if (chunk != null) {
                            out.write(chunk);
                        } else {
                            out.close();
                        }
                    }
                }, Akka.system().dispatcher());

        // Stoppe le job après 10 secondes
        Akka.system().scheduler().scheduleOnce(//
                Duration.create(10, TimeUnit.SECONDS), //
                new Runnable() {
                    public void run() {
                        job.cancel();
                    }
                }, Akka.system().dispatcher());
    }
}