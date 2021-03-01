package br.com.zup.proposal.util.builder;

import br.com.zup.proposal.bloqueio.BloqueiaClient;
import br.com.zup.proposal.proposta.AnaliseClient;
import io.opentracing.Span;
import io.opentracing.SpanContext;
import io.opentracing.tag.Tag;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TesteDataBuilder {


    public static Span getSpan(){
        return new Span() {
            @Override
            public SpanContext context() {
                return null;
            }

            @Override
            public Span setTag(String s, String s1) {
                return null;
            }

            @Override
            public Span setTag(String s, boolean b) {
                return null;
            }

            @Override
            public Span setTag(String s, Number number) {
                return null;
            }

            @Override
            public <T> Span setTag(Tag<T> tag, T t) {
                return null;
            }

            @Override
            public Span log(Map<String, ?> map) {
                return null;
            }

            @Override
            public Span log(long l, Map<String, ?> map) {
                return null;
            }

            @Override
            public Span log(String s) {
                return null;
            }

            @Override
            public Span log(long l, String s) {
                return null;
            }

            @Override
            public Span setBaggageItem(String s, String s1) {
                return null;
            }

            @Override
            public String getBaggageItem(String s) {
                return null;
            }

            @Override
            public Span setOperationName(String s) {
                return null;
            }

            @Override
            public void finish() {

            }

            @Override
            public void finish(long l) {

            }
        };
    }

    public static BloqueiaClient.NovoBloqueioRequest getBloqueioRequest() {

        BloqueiaClient.NovoBloqueioRequest request = new BloqueiaClient.NovoBloqueioRequest("MyAPI");
        return request;
    }

    public static BloqueiaClient.NovoBloqueioResponse getBloqueioResponse() {

        BloqueiaClient.NovoBloqueioResponse response = new BloqueiaClient.NovoBloqueioResponse();
        response.setResultado("BLOQUEADO");
        return response;
    }
}
