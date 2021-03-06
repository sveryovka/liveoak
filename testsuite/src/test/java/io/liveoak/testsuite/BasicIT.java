package io.liveoak.testsuite;

import com.fasterxml.jackson.databind.JsonNode;
import io.liveoak.spi.LiveOak;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author <a href="mailto:sthorger@redhat.com">Stian Thorgersen</a>
 */
public class BasicIT extends AbstractLiveOakTest {

    @Test
    public void getAdminResource() throws Exception {
        JsonNode node = get("/admin");
        Assert.assertEquals("admin", node.get(LiveOak.ID).asText());
        Assert.assertEquals("/admin", node.get(LiveOak.SELF).get(LiveOak.HREF).asText());
    }

}
