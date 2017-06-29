package wsdltest;

public class MyServiceImpl implements MyService {

    @Override
    public String echo(String in) {
        return in;
    }

}
