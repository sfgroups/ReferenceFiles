yieldUnescaped '<!DOCTYPE html>'
html {
    head {
    meta('http-equiv':'"Content-Type" content="text/html; charset=utf-8"')
        meta(attr:'value')
        title(pageTitle)
        style(type:"text/css", '''

           table.paleBlueRows {
             font-family: "Times New Roman", Times, serif;
             border: 1px solid #FFFFFF;
             width: 350px;
             height: 200px;
             text-align: center;
             border-collapse: collapse;
           }
           table.paleBlueRows td, table.paleBlueRows th {
             border: 1px solid #FFFFFF;
             padding: 3px 2px;
           }
           table.paleBlueRows tbody td {
             font-size: 13px;
           }
           table.paleBlueRows tr:nth-child(even) {
             background: #D0E4F5;
           }
           table.paleBlueRows thead {
             background: #0B6FA4;
             border-bottom: 5px solid #FFFFFF;
           }
           table.paleBlueRows thead th {
             font-size: 17px;
             font-weight: bold;
             color: #FFFFFF;
             text-align: center;
             border-left: 2px solid #FFFFFF;
           }
           table.paleBlueRows thead th:first-child {
             border-left: none;
           }

           table.paleBlueRows tfoot {
             font-size: 14px;
             font-weight: bold;
             color: #333333;
             background: #D0E4F5;
             border-top: 3px solid #444444;
           }
           table.paleBlueRows tfoot td {
             font-size: 14px;
           }

         ''')
         newLine()
    }
    body {
    newLine()
        section(id: 'main') {
            // Render mainContents layout property.
            mainContents()
        }
 newLine()
        section(id: 'actions') {
            // Render actions layout property.
            div(){
            actions()
            }
        }
 newLine()

 newLine()
        a(class: 'brand',
          href: 'markup-template-engine.html') {
          yield 'Groovy - Template Engine docs'
        }
newLine()

div(){
            table( 'class="main"'){ //Container table
              tr(){
                td('width':'20','class':'nomob'){
               yield '1'
                }
                td('align':'center'){


            }
          }
          }
          }
newLine()
p(
 values.each { println it}
)
newLine()
        footer {


            div( class:"footer"){
             table(){
                  tr(){
                  td(class:"content-block powered-by"){
                  yield 'certificate alerts'
                  }

                }

              }


            }

        }
        newLine()
    }
}
