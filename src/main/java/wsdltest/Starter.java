package wsdltest;

import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.apache.cxf.transport.http.DestinationRegistry;
import org.apache.cxf.transport.http.HTTPTransportFactory;
import org.apache.cxf.transport.servlet.CXFNonSpringServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Starter {

    public static void main(String[] args) throws Exception {
        Bus bus = BusFactory.newInstance().createBus();
        HTTPTransportFactory ht = bus.getExtension(HTTPTransportFactory.class);
        DestinationRegistry destReg = ht.getRegistry();
        CXFNonSpringServlet servlet = new CXFNonSpringServlet(destReg);
        JaxWsServerFactoryBean factory = new JaxWsServerFactoryBean();
        factory.setBus(bus);
        factory.setServiceClass(MyService.class);
        factory.setServiceBean(new MyServiceImpl());
        factory.setAddress("/test");
        factory.create();
     
        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8384);
        server.addConnector(connector);
        ServletContextHandler handler = new ServletContextHandler();
        handler.setContextPath("/context");
        handler.addServlet(new ServletHolder(servlet), "/cxf/*");
        server.setHandler(handler);
        
        server.start();
        Thread.sleep(100000);
        server.stop();
    }

}
