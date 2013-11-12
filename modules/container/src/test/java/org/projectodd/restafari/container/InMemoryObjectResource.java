package org.projectodd.restafari.container;

import org.projectodd.restafari.spi.RequestContext;
import org.projectodd.restafari.spi.resource.BlockingResource;
import org.projectodd.restafari.spi.resource.async.PropertySink;
import org.projectodd.restafari.spi.resource.async.Resource;
import org.projectodd.restafari.spi.resource.async.Responder;
import org.projectodd.restafari.spi.state.ResourceState;

/**
 * @author Bob McWhirter
 */
public class InMemoryObjectResource implements Resource, BlockingResource {

    public InMemoryObjectResource(InMemoryCollectionResource parent, String id, ResourceState state) {
        this.parent = parent;
        this.id = id;
        this.state = state;
    }

    public Resource parent() {
        return this.parent;
    }

    public String id() {
        return this.id;
    }

    public ResourceState state() {
        return state;
    }

    @Override
    public void delete(RequestContext ctx, Responder responder) {
        this.parent.delete(this.id);
        responder.resourceDeleted(this);
    }

    @Override
    public void readProperties(RequestContext ctx, PropertySink sink) {
        for ( String name : this.state.getPropertyNames() ) {
            sink.accept( name, this.state.getProperty( name ) );
        }
        sink.close();
    }

    @Override
    public void updateProperties(RequestContext ctx, ResourceState state, Responder responder) {
        this.state = state;
        responder.resourceUpdated(this);
    }

    public String toString() {
        return "[InMemoryObjectResource: id=" + this.id + "]";
    }

    private InMemoryCollectionResource parent;
    private String id;
    private ResourceState state;

}
