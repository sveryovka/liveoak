package org.projectodd.restafari.container.resource;

import org.projectodd.restafari.spi.InitializationException;
import org.projectodd.restafari.spi.RequestContext;
import org.projectodd.restafari.spi.ResourceContext;
import org.projectodd.restafari.spi.resource.RootResource;
import org.projectodd.restafari.spi.resource.async.ResourceSink;
import org.projectodd.restafari.spi.resource.async.Responder;

/**
 * @author Bob McWhirter
 */
public class ContainerResource implements  RootResource {


    public ContainerResource(String id) {
        this.id = id;
        this.propertiesResource = new PropertiesResource( this );
    }

    @Override
    public void initialize(ResourceContext context) throws InitializationException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void readMembers(RequestContext ctx, ResourceSink sink) {
        sink.accept( this.propertiesResource );
        try {
            sink.close();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Override
    public String id() {
        return this.id;
    }

    @Override
    public void readMember(RequestContext ctx, String id, Responder responder) {
        if ( id.equals( this.propertiesResource.id() ) ) {
            responder.resourceRead( this.propertiesResource );
        } else {
            responder.noSuchResource( id );
        }
    }

    private String id;

    private PropertiesResource propertiesResource;
}
