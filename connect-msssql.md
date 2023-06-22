using System;
using System.Data.SqlClient;

class Program
{
    static void Main()
    {
        string connectionString = "Data Source=<server_name>;Initial Catalog=<database_name>;Integrated Security=True;";
        
        try
        {
            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                connection.Open();
                Console.WriteLine("Connected to the database.");
                // Perform database operations here
            }
        }
        catch (Exception ex)
        {
            Console.WriteLine("Error: " + ex.Message);
        }

        Console.ReadLine();
    }
}


dotnet build
dotnet run

====

#!/bin/bash

#set -xv
USER_ID="$1"
USER_PASS="$DBPASSWORD"

if [ -z "$USER_ID" -o "${#USER_ID}" -lt 3 ]; then
	echo "User Name is empty or less than 3 character length"
	exit 1
fi

if [ -z "$USER_PASS" -o "${#USER_PASS}" -lt 5 ]; then
	echo "Password is empty or less than 5 character length"
	exit 1
fi

rm -f "${USER_ID}.keytab"
# Create Keytab file
printf "add_entry -password -p ${USER_ID}@DOMIAN.COM -k 1 -e RC4-HMAC\n$USER_PASS\nwkt ${USER_ID}.keytab" | ktutil

# Test keytab file
kinit "${USER_ID}" -k -t  "${USER_ID}.keytab"
if [ $? -ne 0 ]; then
	echo "Unable to validate keytab file"
	exit 1
else
	echo -n "=========== Password is GOOD ==========="
	klist
fi

KEYTAB_FILE="client.keytab"

echo "====== Command to create keytable file ============="
cat <<-EOF
# to Test

kinit ${USER_ID} -k -t ${USER_ID}.keytab

# Debug
KRB5_TRACE=/dev/stdout kinit username #May help you troubleshoot


===
FROM debian:bookworm-slim

RUN apt-get --assume-yes update
RUN apt-get --assume-yes upgrade
RUN apt-get --assume-yes dist-upgrade

RUN apt-get remove krb5-config krb5-user

# Install Kerberos libraries
RUN apt-get install -y krb5-config  krb5-user kstart

ADD krb5.conf /etc/krb5.conf
ADD rekinit.sh /app/rekinit.sh
RUN groupadd  -g 1000 -r webgroup && \
    useradd -u 1000 -r -d /home/webuser -s /bin/sh -g webgroup webuser && \
    mkdir -m 775 -p /app/secret /krb5 && \
    chmod go+r /etc/krb5.conf && \
    chown webuser:webgroup /app /krb5 /app/rekinit.sh /app/secret

WORKDIR /app

USER webuser

ENTRYPOINT ["/app/rekinit.sh"]

===
#!/bin/bash

[[ "$PERIOD_SECONDS" == "" ]] && PERIOD_SECONDS=5

KEYTAB_FILE="/app/secret/client.keytab"
if [ ! -e "${KEYTAB_FILE}" ]; then
	echo "File not found: $KEYTAB_FILE"
	sleep 1
	exit 0
fi

if [[ "$OPTIONS" == "" ]]; then
  [[ -e /krb5/client.keytab ]] && OPTIONS="-k -i" && echo "*** using client keytab"

fi

if [[ -z "$(ls -A /app/secret)" ]]; then
  echo "*** Warning default client keytab (/app/secret/client.keytab) not found"
fi

echo "Keytab file : $KEYTAB_FILE"

while true
do
  echo "*** kstart at "+$(date -I)
   #kinit -V $OPTIONS $APPEND_OPTIONS
   k5start -f ${KEYTAB_FILE}  -v -K 10 -aU 
   klist 

   echo "*** Waiting for $PERIOD_SECONDS seconds : $(date +'%D %R')" 
   sleep $PERIOD_SECONDS

