import service.ClientCrudService;
import service.PlanetCrudService;
import service.HibernateUtil;
import org.flywaydb.core.Flyway;
import service.TicketCrudService;

public class Main {
    public static void main(String[] args) {
        final String url = "jdbc:h2:./module11DevHibernateMapping";

        Flyway.configure()
                .dataSource(url, null, null)
                .load()
                .migrate();

        ClientCrudService clientCrudService = new ClientCrudService();
        PlanetCrudService planetCrudService = new PlanetCrudService();
        TicketCrudService ticketCrudService = new TicketCrudService();

        ticketCrudService.create("2023-06-29T17:00:10Z",
                clientCrudService.get(2),
                planetCrudService.get("VEN"),
                planetCrudService.get("EAR"));
        System.out.println(ticketCrudService.get(11));
        ticketCrudService.update(clientCrudService.get(2), 9);
        ticketCrudService.delete(3);


        ticketCrudService.create("2023-06-29T17:12:10Z",
                null,
                planetCrudService.get("EAR"),
                planetCrudService.get("VEN"));

        ticketCrudService.create("2023-06-29T17:20:10Z",
                clientCrudService.get(1),
                null,
                planetCrudService.get("EAR"));

        HibernateUtil.getInstance().close();
    }
}
