package io.liveoak.scripts.resourcetriggered.manager;

import io.liveoak.scripts.libraries.manager.LibraryManager;
import io.liveoak.scripts.resource.ScriptsRootResource;
import io.liveoak.scripts.resourcetriggered.interceptor.ScriptInterceptor;
import io.liveoak.scripts.resourcetriggered.resource.ScriptRegistry;
import org.jboss.msc.service.Service;
import org.jboss.msc.service.StartContext;
import org.jboss.msc.service.StartException;
import org.jboss.msc.service.StopContext;
import org.jboss.msc.value.InjectedValue;

/**
 * @author <a href="mailto:mwringe@redhat.com">Matt Wringe</a>
 */
public class ResourceScriptManagerService implements Service<ResourceScriptManager> {

    private ResourceScriptManager scriptManager;

    @Override
    public void start(StartContext startContext) throws StartException {
        this.scriptManager = new ResourceScriptManager(scriptRegistryInjector.getValue(), scriptRootInjector.getValue().getScriptConfig(), libraryManagerInjector.getValue());
        // add the manager to the script interceptor
        interceptorInjector.getValue().addManager(applicationNameInjector.getValue(), this.scriptManager);
    }

    @Override
    public void stop(StopContext stopContext) {
        // remove the manager from the script interceptor
        interceptorInjector.getValue().removeManager(applicationNameInjector.getValue());
        this.scriptManager = null;
    }

    @Override
    public ResourceScriptManager getValue() throws IllegalStateException, IllegalArgumentException {
        return scriptManager;
    }

    public InjectedValue<LibraryManager> getLibraryManagerInjector() {
        return libraryManagerInjector;
    }

    public InjectedValue<ScriptInterceptor> getInterceptorInjector() {
        return interceptorInjector;
    }

    public InjectedValue<ScriptRegistry> getScriptRegistryInjector() {
        return scriptRegistryInjector;
    }

    public InjectedValue<String> getApplicationNameInjector() {
        return applicationNameInjector;
    }

    public InjectedValue<ScriptsRootResource> getScriptRootInjector() {
        return scriptRootInjector;
    }

    private InjectedValue<LibraryManager> libraryManagerInjector = new InjectedValue<>();
    private InjectedValue<ScriptInterceptor> interceptorInjector = new InjectedValue<>();
    private InjectedValue<ScriptRegistry> scriptRegistryInjector = new InjectedValue<>();
    private InjectedValue<String> applicationNameInjector = new InjectedValue<>();
    private InjectedValue<ScriptsRootResource> scriptRootInjector = new InjectedValue<>();
}
