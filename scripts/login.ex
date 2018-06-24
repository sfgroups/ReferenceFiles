#!/usr/bin/expect

if {[llength $argv] != 2} {
        puts "usage: login <username> <remotehost>"
        exit
}

if { ! [info  exists ::env(UPASS) ] } {
        puts "User password UPASS environemnt varible not set"
        exit
 } 

if { ! [info  exists ::env(RPASS) ] } {
        puts "ROOT user password RPASS environemnt varible not set"
#        exit
 } 

if { ! [info  exists ::env(DISPLAY) ] } {
        puts "DISPLAY environemnt varible not set"
#        exit
 }

set username [lindex $argv 0 ]
set servername [lindex $argv 1 ]

# Server prompt after login
set prompt "\\$|#"
match_max 100000
set timeout 30
#exp_internal 1
spawn ssh $username@$servername
expect {
	-nocase	-re  "continue connecting" {
		send "yes\r"
                exp_continue
	} -nocase -re "password" {
		#Read password from Environment varible
		send "$env(UPASS)\r"
		exp_continue
	} incorrect {
		send_user "invalid password or account\n"
		exit
	} timeout {
		send_user "connection to $host timed out\n"
               	exit
	} eof {
		send_user "connection to host failed: $expect_out(buffer)"
		exit
	} -re $prompt {
		send_user "got prompt\n"
	}
}

#exp_internal 1
#Login as Super User
send "sudo su - \r"
expect {
	 -nocase  -re "assword" {
		send "$env(UPASS)\r"
		exp_continue
	} -re $prompt {
		send_user "got prompt\r"
		send "\r"
	}
}

interact
