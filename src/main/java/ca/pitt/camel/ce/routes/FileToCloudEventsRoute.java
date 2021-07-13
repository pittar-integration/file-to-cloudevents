package ca.pitt.camel.ce.routes;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.BindyType;
import org.apache.camel.model.dataformat.JsonLibrary;

import ca.pitt.camel.ce.dto.DataDTO;

public class FileToCloudEventsRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        // Consume book CSV files
        from("file:{{file.location}}?delay=1000&idempotent=true&noop=true")
            .log("File contents: ${body}")
            .unmarshal().bindy(BindyType.Csv, DataDTO.class)
            .split(body())
                .to("direct:postcloudevents")
            .end();

        from("direct:postcloudevents")
            .setHeader("CamelHttpMethod", constant("POST"))
            .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
            .setHeader("Ce-Id", constant("fuse-names"))
            .setHeader("Ce-Specversion", constant("1.0"))
            .setHeader("Ce-Type", constant("names"))
            .setHeader("Ce-source", constant("Fuse"))
            .marshal().json(JsonLibrary.Jackson)
            .to("{{K_SINK}}");
    }
    
}
