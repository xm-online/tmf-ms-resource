package com.icthh.xm.tmf.ms.resource.config;

import static org.assertj.core.api.Assertions.assertThat;

import com.icthh.xm.commons.i18n.error.domain.vm.ParameterizedErrorVM;
import com.icthh.xm.tmf.ms.resource.AbstractSpringBootTest;
import com.icthh.xm.tmf.ms.resource.web.api.model.LogicalResource;
import com.icthh.xm.tmf.ms.resource.web.api.model.LogicalResourceCreate;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

/**
 * Request and response JSON must stay as it was with Jackson 2 / openapi-generator 4 (compared with master).
 */
public class JacksonCompatibilityIntTest extends AbstractSpringBootTest {

    @Autowired
    private JsonMapper jsonMapper;

    @Test
    public void modelKeepsDeclarationOrderNullsAndNullCollections() {
        LogicalResource model = new LogicalResource();
        model.setId("1");

        JsonNode json = jsonMapper.readTree(jsonMapper.writeValueAsString(model));

        assertThat(json.propertyNames()).containsExactly("@baseType", "@schemaLocation", "@type", "category",
            "description", "endDate", "href", "id", "lifecycleState", "name", "startDate", "version", "value",
            "resourceRelationship", "characteristic", "place", "partyRole", "relatedParty", "note", "resourceStatus");
        assertThat(json.get("note").isNull()).isTrue();
        assertThat(json.get("name").isNull()).isTrue();
    }

    @Test
    public void absentFieldsKeepModelDefaults() {
        LogicalResourceCreate request = jsonMapper.readValue("{\"name\":\"x\",\"unknown\":1}", LogicalResourceCreate.class);

        assertThat(request.getName()).isEqualTo("x");
        assertThat(request.getNote()).isNull();
        assertThat(request.getRelatedParty()).isNull();
    }

    @Test
    public void businessErrorKeepsPropertyOrder() {
        JsonNode json = jsonMapper.readTree(jsonMapper.writeValueAsString(
            new ParameterizedErrorVM("error.code", "message", Map.of())));

        assertThat(json.propertyNames()).containsExactly("error", "error_description", "requestId", "params");
    }
}
