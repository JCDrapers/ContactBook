import groovy.swing.SwingBuilder
import javax.swing.WindowConstants as WC
import javax.swing.JOptionPane
import javax.swing.JScrollPane
import javax.swing.BoxLayout as BXL
import groovy.sql.Sql

this.getClass().classLoader.rootLoader.addURL(
        new File("sqlite-jdbc-3.23.1.jar").toURL())

def sql = Sql.newInstance( 'jdbc:sqlite:databasefile.sqlite',
        'org.sqlite.JDBC' )


def params = [10, 'Groovy', 'http://groovy.codehaus.org']

sql.execute """
create table file_info (
  pkey integer primary key,
  filename text,
  filesize integer
)"""

def Names = ["James","Jhon","Jim","Jerry","Barry","Bill","Bob","Bill","Brexit","Bowser","Tara","Angry Cat","Neon Cat","Pepper","Freddie","Dog","Cat","Tiger"] as String[]
def arrayLength = (Names.length - 1)
def display_arraylength = (arrayLength + 1)
println(arrayLength)
int numPanels = arrayLength

swing = new SwingBuilder()
frame = swing.frame(title:"James' Contact Book", pack:true, visible:true, defaultCloseOperation:WC.HIDE_ON_CLOSE) {
    panel(id:'mainPanel'){
        scrollPane( verticalScrollBarPolicy:JScrollPane.VERTICAL_SCROLLBAR_ALWAYS ) {
            vbox {
                label("    You have $display_arraylength contacts    ")
                label("    Here are their names, press more to see more information     ")

                (0..numPanels).each { num ->
                    def Contact = Names[num]
                    def panelID = "Contact $Contact"
                    def pane = panel( alignmentX:0f, id:panelID, background:java.awt.Color.LIGHT_GRAY ) {
                        label("$Contact")
                        button( id: "buttonpanel$num", text:"More", actionPerformed:{
                            frame.setEnabled(false);
                            swing."$panelID".background = java.awt.Color.RED;
                            frame2 = swing.frame(title:"$Contact's Information", pack:true, visible:true, defaultCloseOperation:WC.DO_NOTHING_ON_CLOSE) {
                                panel(id:'secondPanel') {
                                    vbox {
                                        def contact_number = (num+1)
                                        label("$Contact is contact number  $contact_number , press OK to close this window")
                                        button( id: "buttonpanel$num", text:"OK", actionPerformed: {
                                            frame.setEnabled(true);
                                            frame2.visible = false
                                            swing."$panelID".background = java.awt.Color.LIGHT_GRAY;
                                        }
                                        )
                                    }
                                }
                            }
                        }
                        )
                    }
                }
            }
        }

        boxLayout(axis: BXL.Y_AXIS)
        panel(id:'secondPanel' , alignmentX: 0f){
            button('Quit', actionPerformed:{
                frame.visible = false
            })
        }
    }
}
frame.size = [ frame.width, 600 ]