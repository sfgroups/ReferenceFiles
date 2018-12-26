package template

import groovy.json.JsonSlurper
import groovy.xml.MarkupBuilder
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path
import java.text.SimpleDateFormat
import groovy.text.*
import groovy.text.markup.*

class MarkupBuilder01 {

    def static main(args){
       Run()
    }

    def static Run(){
        TemplateConfiguration config = new TemplateConfiguration();
        MarkupTemplateEngine engine = new MarkupTemplateEngine(config);
        Template template = engine.createTemplate("p('test template')");
        Map<String, Object> model = new HashMap<>();
        model.put('title','Title from main model');
        Writer writer = new StringWriter()
        Writable output = template.make(model);
        output.writeTo(writer)
        String result = writer.toString()
        println result
    }

    def static Test01(){
        StringWriter writer = new StringWriter()
        def build = new MarkupBuilder(writer)
        build.html{
            head{
                style(type:"text/css", '''
    .bigPaddingAndGreen {
        margin: 30px;
        padding: 30px;
        background-color: #00FF00
    }
    ''')
            }
            body{
                table{
                    tr{
                        td('class':'bigPaddingAndGreen', "Our very own moose:")
                        td('class':'bigPaddingAndGreen'){
                            img(src:"", border:0)
                        }
                    }
                }
            }
        }
        println writer.toString()
    }

    def static PrintTable(){
        def inputFile ='''data: [
                {
                    "kubernetes.pod.name": "sds-endpoints-6-hn0fe2l",
                    "container.id": "d19e001824978",
                    "memory.used.percent": 102,
                    "cpu.used.percent": 7,
                    "memory.bytes.used (mB)": 2067,
                    "cpu.cores.used (millicores)": 9,
                    "endTime": "2018-07-04T02:00:00+0000"
                },
                {
                    "kubernetes.pod.name": "product-service-endpoints-4-da1w",
                    "container.id": "4dd6447f5e14",
                    "memory.used.percent": 84,
                    "cpu.used.percent": 7,
                    "memory.bytes.used (mB)": 1698,
                    "cpu.cores.used (millicores)": 8,
                    "endTime": "2018-07-04T02:00:00+0000"}
        ]'''

        def InputJSON = new JsonSlurper().parseText(inputFile)


        def writer = new StringWriter()  // html is written here by markup builder
        def markup = new groovy.xml.MarkupBuilder(writer)  // the builder

// MAKE OF HTML
        markup.html{
            markup.table(class:"table table-bordered table-hover table-condensed") {
                markup.thead{
                    markup.tr {
                        markup.th(title:"Field #1", "kubernetes.pod.name")
                        markup.th(title:"Field #2", "container.id")
                        markup.th(title:"Field #3", "memory.used.percent")
                        markup.th(title:"Field #4", "cpu.used.percent")
                        markup.th(title:"Field #5", "memory.bytes.used (mB)")
                        markup.th(title:"Field #6", "cpu.cores.used (millicores)")
                        markup.th(title:"Field #7", "endTime")
                    } // tr
                } // thead
                markup.tbody{
                    markup.tr{ for (def data : InputJSON.data) {
                        markup.tr{
                            markup.td(align:"right",data.d[0])
                            markup.td(align:"right",data.d[1])
                            markup.td(align:"right",Math.round((data.d[2]) * 100))
                            markup.td(align:"right",Math.round((data.d[3]) * 100))
                            markup.td(align:"right",Math.round((data.d[4]) * 0.000001))
                            markup.td(align:"right",Math.round(((data.d[5]) * 1000)))
                            markup.td(align:"right",new Date(((long) InputJSON.end) * 1000))
                        } // foreach
                    } // td
                    } // tr
                } //tbody
            } // table
        }

        println writer.toString()
    }
}
