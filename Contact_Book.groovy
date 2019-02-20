//Import Swing Dependencies
import groovy.swing.SwingBuilder
import javax.swing.WindowConstants as WC
import javax.swing.JOptionPane
import javax.swing.JScrollPane
import javax.swing.JOptionPane
import javax.swing.JScrollPane
import javax.swing.BoxLayout as BXL
import groovy.swing.SwingBuilder
import javax.swing.*
import java.awt.*

//Import SQL Dependencies
import groovy.sql.Sql
import java.sql.*
//import org.sqlite.SQLite

//SQLite force get JDBC file ( Grapes isn't needed; but i like grapes)
@Grapes([
        @Grab(group='org.xerial',module='sqlite-jdbc',version='3.23.1'),
        @GrabConfig(systemClassLoader=true)
])

//Import Font Dependencies
import java.awt.Font


//SQL Setup
def sql = Sql.newInstance("jdbc:sqlite:Contacts.db", "org.sqlite.JDBC")

def contact = sql.dataSet("Contacts")
//people.add(id:2,name:'yui')


//sql.eachRow("select * from Contacts") {
//    println("$it.id and $it.name")
//}


//Get number of contacts in database
def result = sql.firstRow('select count(*) as cont from Contacts WHERE name != "none"')
int Number_Of_Contacts = result.cont

//Fonts
    Font title = new Font("Serif", Font.BOLD, 30)
    Font sub_title = new Font("Serif", Font.BOLD, 17)
    Font Name = new Font("Serif", Font.BOLD, 20)
    Font Ok_Button = new Font("Serif", Font.BOLD, 15)
    Font Contact_Add_Button = new Font("Serif", Font.BOLD, 15)
    Font bottom_buttons = new Font("Serif", Font.BOLD, 14)

    swing = new SwingBuilder()
    frame = swing.frame(title: "James' Contact Book", pack: true, visible: true, defaultCloseOperation: WC.HIDE_ON_CLOSE) {
        panel(id: 'mainPanel') {
            scrollPane(verticalScrollBarPolicy: JScrollPane.VERTICAL_SCROLLBAR_ALWAYS) {
                vbox {
                    label(" You have $Number_Of_Contacts contacts", constraints: "align center, span 6").setFont(title)
                    label("  Press more to see more information").setFont(sub_title)

                    (0..Number_Of_Contacts+100).each { num ->

                        def pane = panel(alignmentX: 0f, background: java.awt.Color.LIGHT_GRAY, layout: new GridLayout(1, 2)) {
                            sql.eachRow("select rowid, * from Contacts WHERE rowid = $num+1") {
                                if ("$it.name" != 'none'){
                                    println("$it.rowid")
                                    println("$it.name")
                                    label("   $it.name").setFont(Name)

                                }
                                if ("$it.name" != 'none'){
                                    more = button(id: "buttonpanel$num", text: "More", actionPerformed: {
                                        frame.setEnabled(false);
                                        frame2 = swing.frame(title: "Information",pack: true, visible: true, defaultCloseOperation: WC.DO_NOTHING_ON_CLOSE) {
                                            panel(id: 'secondPanel') {
                                                vbox {
                                                    //def contact_number = (num + 1)
                                                    sql.eachRow("select rowid, * from Contacts WHERE rowid = $num+1") {
                                                        contact_num = num+1
                                                        //Name
                                                        label("Name:  $it.name  ").setFont(Name)
                                                        // group
                                                        label("Group:  $it.groups  ").setFont(Name)
                                                        // Mobile number
                                                        label("Mobile Number:  $it.mobile  ").setFont(Name)
                                                        //Home number
                                                        label("Home Number: $it.home  ").setFont(Name)
                                                        //Email
                                                        label("Email: $it.email  ").setFont(Name)
                                                        //Address
                                                        label("Address: $it.address  ").setFont(Name)
                                                    }
                                                    ok = button(id: "buttonpanel$num", text: "OK", actionPerformed: {
                                                        frame.setEnabled(true);
                                                        frame2.visible = false
                                                    }
                                                    )
                                                    ok.setFocusPainted(false);
                                                    ok.setBackground(new Color(4, 224, 129));
                                                    ok.setFont(Ok_Button)
                                                }
                                            }
                                        }
                                    }
                                    )
                                    more.setMargin(new Insets(10, 10, 10, 10));
                                    more.setFocusPainted(false);
                                    more.setBackground(new Color(4, 224, 129));
                                    more.setFont(Name)
                                }


                                if ("$it.name" != 'none'){
                                    remove_button = button(id: "buttonpanel$num", text: "Remove", actionPerformed: {
                                        frame.setEnabled(false)
                                        frame3 = swing.frame(title: "Remove?", pack: true, visible: true, defaultCloseOperation: WC.DO_NOTHING_ON_CLOSE) {
                                            panel(id: 'thirdPanel') {
                                                vbox {
                                                    println("$num")
                                                    sql.eachRow("select rowid, * from Contacts WHERE rowid = $num+1") {

                                                        label("  Are you sure you want to remove $it.name?  ")

                                                        remove_confirm = button(id: "buttonpanel$num", text: "Remove $it.name", actionPerformed: {
                                                            sql.eachRow("select rowid, * from Contacts WHERE rowid = $num+1") {
                                                                sql.execute("UPDATE Contacts SET name = 'none' WHERE name = $it.name")
                                                            }
                                                            frame.setEnabled(true);
                                                            frame3.visible = false
                                                        }
                                                        )
                                                    }

                                                    remove_confirm.setFocusPainted(false);
                                                    remove_confirm.setBackground(new Color(200, 4, 21));
                                                    remove_confirm.setFont(Ok_Button)

                                                    remove_deny = button(id: "buttonpanel$num", text: "Cancel", actionPerformed: {
                                                        frame.setEnabled(true);
                                                        frame3.visible = false
                                                    }
                                                    )
                                                    remove_deny.setFocusPainted(false);
                                                    remove_deny.setBackground(new Color(4, 224, 129));
                                                    remove_deny.setFont(Ok_Button)

                                                }

                                            }
                                        }

                                    }
                                    )

                                    //"Remove" Button Styles
                                    remove_button.setFocusPainted(false);
                                    remove_button.setBackground(new Color(200, 4, 21));
                                    remove_button.setFont(Name)
                                }
                                }









                        }

                    }
                }
            }

            boxLayout(axis: BXL.Y_AXIS)
            panel(id: 'secondPanel', alignmentX: 0f) {

                add_contact = button('Add Contact', constraints: BorderLayout.CENTER, actionPerformed: {
                    frame.setEnabled(false);
                    frame4 = swing.frame(title: "Add Contact",pack: true, visible: true, defaultCloseOperation: WC.DO_NOTHING_ON_CLOSE) {
                        panel(id: 'Add_Contact', layout: new GridLayout(1, 1)) {
                            vbox {

                                label "Contact's Information : "
                                label '                      '
                                label 'Name : '
                                name = textField(columns: 2, actionPerformed: { name.text() })

                                label 'Group : '
                                group = textField(columns: 10, actionPerformed: { group.text() })

                                label 'Mobile Number : '
                                mobile = textField(columns: 10, actionPerformed: { mobile.text() })

                                label 'Home Number : '
                                home = textField(columns: 10, actionPerformed: { home.text() })

                                label 'Email Address: '
                                email = textField(columns: 10, actionPerformed: { email.text() })

                                label 'Address : '
                                address = textField(columns: 10, actionPerformed: { address.text() })

                                label '                      '

                            }

                            add_contact_button = button(id: "Contact_added", text: "Add Contact", actionPerformed: {

                                sql.execute("INSERT INTO CONTACTS (NAME,GROUPS,mobile,home,email,address) values ($name.text,$group.text,$mobile.text,$home.text,$email.text,$address.text)")
                                frame.setEnabled(true);
                                frame4.visible = false
                            }
                            )
                            add_contact_button.setFocusPainted(false);
                            add_contact_button.setBackground(new Color(4, 224, 129));
                            add_contact_button.setFont(Contact_Add_Button)

                        }
                    }

                }
                )

                quit_button = button('Quit', constraints: BorderLayout.CENTER, actionPerformed: {
                    frame.visible = false
                })
                add_contact.setFocusPainted(false);
                add_contact.setBackground(new Color(1, 186, 51));
                add_contact.setFont(bottom_buttons)
                quit_button.setFocusPainted(false);
                quit_button.setBackground(new Color(150, 50, 50));
                quit_button.setFont(bottom_buttons)
            }
        }
    }

//frame.size = [ 500, 600 ]
