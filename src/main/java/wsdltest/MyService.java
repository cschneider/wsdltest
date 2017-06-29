package wsdltest;

import javax.jws.WebService;

@WebService
public interface MyService {
    String echo(String in);
}
