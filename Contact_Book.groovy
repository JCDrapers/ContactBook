import groovy.swing.SwingBuilder
import javax.swing.WindowConstants as WC
import javax.swing.JOptionPane
import javax.swing.JScrollPane
import javax.swing.BoxLayout as BXL
import groovy.sql.Sql
import java.awt.Font

import groovy.swing.SwingBuilder
import javax.swing.*
import java.awt.*










//Fonts
Font title = new Font("Serif", Font.BOLD, 30)
Font sub_title = new Font("Serif", Font.BOLD, 17)
Font Name = new Font("Serif", Font.BOLD, 23)
Font Ok_Button = new Font("Serif", Font.BOLD, 15)


def Names = ["James","Jhon","Jim","Jerry","Barry","Bill","Bob","Bill","Brexit","Bowser","Tara","Angry Cat","Neon Cat","Pepper","Freddie","Dog","Cat","Tiger","James","Jhon","Jim","Jerry","Barry","Bill","Bob","Bill","Brexit","Bowser","Tara","Angry Cat","Neon Cat","Pepper","Freddie","Dog","Cat","Tiger"] as String[]
def arrayLength = (Names.length - 1)
def display_arraylength = (arrayLength + 1)
println(arrayLength)
int numPanels = arrayLength

swing = new SwingBuilder()
frame = swing.frame(title:"James' Contact Book", pack:true, visible:true, defaultCloseOperation:WC.HIDE_ON_CLOSE) {
    panel(id:'mainPanel'){
        scrollPane( verticalScrollBarPolicy:JScrollPane.VERTICAL_SCROLLBAR_ALWAYS ) {
            vbox {
                label("         You have $display_arraylength contacts    ").setFont(title)
                label("                  Press more to see more information     ").setFont(sub_title)

                (0..numPanels).each { num ->
                    def Contact = Names[num]
                    def panelID = "Contact $Contact"
                    def pane = panel( alignmentX:0f, id:panelID, background:java.awt.Color.LIGHT_GRAY, layout: new GridLayout(1, 2 )) {
                        label("   $Contact   ").setFont(Name)
                        more = button( id: "buttonpanel$num", text:"More", actionPerformed:{
                            swing."$panelID".background = java.awt.Color.RED;
                            frame.setEnabled(false);
                            frame2 = swing.frame(title:"$Contact's Information", pack:true, visible:true, defaultCloseOperation:WC.DO_NOTHING_ON_CLOSE) {
                                panel(id:'secondPanel') {
                                    vbox {
                                        def contact_number = (num+1)
                                        label("$Contact is contact number  $contact_number , press OK to close this window")
                                        ok = button( id: "buttonpanel$num", text:"OK", actionPerformed: {
                                            frame.setEnabled(true);
                                            frame2.visible = false
                                            swing."$panelID".background = java.awt.Color.LIGHT_GRAY;
                                        }
                                        )
                                        ok.setBackground(new Color(4, 224, 129));
                                        ok.setFont(Ok_Button)
                                    }
                                }
                            }
                        }
                        )
                        more.setBackground(new Color(4, 224, 129));
                        more.setFont(Name)
                        remove = button(id: "buttonpanel$num", text:"Remove", actionPerformed:{
                            println("text")

                        }



                        )
                        remove.setBackground(new Color(200, 4, 21));
                        remove.setFont(Name)
                        }
                }
            }
        }

        boxLayout(axis: BXL.Y_AXIS)
        panel(id:'secondPanel' , alignmentX: 0f){
            button('Quit',constraints : BorderLayout.CENTER, actionPerformed:{
                frame.visible = false
            })
            button('Add Contact',constraints : BorderLayout.CENTER, actionPerformed:{
                frame.visible = false
            })
        }
    }
}
frame.size = [ frame.width, 600 ]