package models;

import play.Logger;
import play.db.jpa.JPA;
import utils.PerfLogger;
import utils.ThreadSafeJPA;
import utils.VideostoreConstants;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Report {

    /**
     * Id technique du rapport
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    public String name;

    @Lob
    public byte[] content;

    @Temporal(TemporalType.TIMESTAMP)
    public Date generationDate;

    public Report() {
    }

    public Report(String name) {
        this.name = name;
    }

    public void execute() {
        generationDate = new Date();
        long start = generationDate.getTime();
        Logger.info("Commence la génération du rapport [{}] à [{}]", name,
                VideostoreConstants.HOUR_FORMATTER.format(generationDate));
        try {
            // Simule une tâche longue en suspendant la thread courante 5 secondes
            content = ("Le contenu du rapport [" + name + "]").getBytes();
            Thread.sleep(5000);

            final Report r = this;
            ThreadSafeJPA.withTransaction(false, new ThreadSafeJPA.Block<Void>() {
                @Override
                public Void apply(EntityManager em) {
                    Logger.info("Référence de l'em utilisé [{}], thread [{}]", em, Thread.currentThread().getName());
                    em.persist(r);
                    return null;
                }
            });
        } catch (Throwable e) {
            e.printStackTrace();
        }
        Logger.info("Termine la génération du rapport [{}] à [{}]", name,
                VideostoreConstants.HOUR_FORMATTER.format(new Date()));
        Logger.info("La tâche a pris {}s", ((System.currentTimeMillis() - start) / 1000));
    }

    @Override
    public String toString() {
        return name;
    }

    public String getChunk(int i) {
        try {
            String content = new String(this.content);
            String chunk = content.split("\\s")[i];
            return chunk;
        } catch (Exception e) {
        }
        return null;
    }

    /*
     * ############################################# JPA #############################################
     */

    /**
     * Récupération d'un Report par son identifiant
     *
     * @param id identifiant du Report
     * @return le Report correspondant à l'identifiant
     */
    public static Report findById(Long id) {
        PerfLogger perf = PerfLogger.start("database.report.findById");
        Report result = JPA.em().find(Report.class, id);
        perf.stop();
        return result;
    }

    /**
     * Sauvegarde ce Report.
     */
    public void save() {
        PerfLogger perf = PerfLogger.start("database.report.save[{}]", this);
        JPA.em().persist(this);
        perf.stop();
    }

    /**
     * Supprime ce Report.
     */
    public void delete() {
        PerfLogger perf = PerfLogger.start("database.report.delete[{}]", this);
        JPA.em().remove(this);
        perf.stop();
    }
}