import time
import random
from kafka import KafkaProducer
from kafka.errors import KafkaError

topic = 'toScreen'

def run(context):
    try:
        producer = KafkaProducer(bootstrap_servers='kafka:9092')
        producer.send(topic, 'New data saved : ' + context )
        time.sleep(1)
        producer.send(topic, 'Doing something with it...') 
        time.sleep(1)
        producer.send(topic, 'Sent ' + context + ' to backup')
    except KafkaError as e:
           print("**ERROR**"+str(e))
    finally:
        producer.close()


