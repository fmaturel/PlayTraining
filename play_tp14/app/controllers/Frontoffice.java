package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimaps;
import models.Film;
import play.cache.Cache;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;
import java.util.concurrent.Callable;

public class Frontoffice extends Controller {

    /**
     * This result directly redirect to application home.
     */
    public static Result GO_HOME = redirect(routes.Frontoffice.list("tous", 4));

    /**
     * Cache duration in seconds
     */
    private static final int DURATION = 60;

    /**
     * Get the list of {@link Film}.
     *
     * @throws Exception
     */
    // @Cached(key = "list", duration = DURATION) => Pose un problème car ne tient pas compte du paramètrage
    @BodyParser.Of(BodyParser.Json.class)
    @play.db.jpa.Transactional(readOnly = true)
    public static Result list(final String genre, final int size) throws Exception {
        return Cache.getOrElse("list." + genre + ".size" + size, new Callable<Result>() {
            @Override
            public Result call() throws Exception {
                play.Logger.info("La requête [{}] ne fait pas appel au Cache", request().path());
                return ok(Json.toJson(Film.findBy(genre, size)));
            }
        }, DURATION);
    }

    /**
     * Get a {@link Film} by id.<br>
     * Can use @Cached annotation as
     *
     * @throws Exception
     */
    // @Cached(key = "detail", duration = DURATION) => Pose un problème car ne tient pas compte du paramètrage
    @BodyParser.Of(BodyParser.Json.class)
    @play.db.jpa.Transactional(readOnly = true)
    public static Result get(final Long id) throws Exception {
        return Cache.getOrElse("detail." + id, new Callable<Result>() {
            @Override
            public Result call() throws Exception {
                play.Logger.info("La requête [{}] ne fait pas appel au Cache", request().path());
                return ok(Json.toJson(Film.findById(id)));
            }
        }, DURATION);
    }

    /**
     * Get a list of {@link Film} for search criterions.
     */
    @BodyParser.Of(BodyParser.Json.class)
    @play.db.jpa.Transactional(readOnly = true)
    public static Result search(String query) {
        return ok(Json.toJson(Film.search(query)));
    }

    /**
     * Order a number of {@link Film}.
     */
    @BodyParser.Of(BodyParser.Json.class)
    @play.db.jpa.Transactional
    public static Result order() throws Exception {
        JsonNode json = request().body().asJson();
        try {
            play.Logger.info("Passe la commande: {}", json);
            Order order = Json.fromJson(json, Order.class);
            play.Logger.info("Interprété la commande: {}", order);

            if (order != null && order.items != null && !order.items.isEmpty()) {
                Film.order(order);
            }
        } catch (Exception e) {
            throw e;
        }
        return ok();
    }

    public static class Order {

        public List<Item> items;

        public static class Item {
            public Long id;
            public Integer nb;

            @Override
            public String toString() {
                return "Item [id=" + id + ", nb=" + nb + "]";
            }
        }

        public ImmutableListMultimap<Long, Item> getItemsByIds() {
            Function<Item, Long> itemsToIds = new Function<Item, Long>() {
                public Long apply(Item item) {
                    return item.id;
                }
            };
            return Multimaps.index(items, itemsToIds);
        }

        @Override
        public String toString() {
            return "Items [items=" + items + "]";
        }
    }
}
