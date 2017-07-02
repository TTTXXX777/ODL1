mypacket project
=================
This is the Opendaylight demo project for sending any raw packet-data to network.
It works on :
* Opendaylight version: Boron-SR1, Beryllium-SR+, and previous version


# Introduction
In Opendaylight, "PacketProcessingService" interface provide the ability to send any raw packet-data to network.
This service can be obtained from "model-flow-service" with bundle openflowplugin.

Rest API of "packet-processing(2013-07-09)" is another method to deliver raw packet-data from controller 
through specific switch to network. A concrete JSON format is giving below.

## PacketProcessingService
The service is provided by openflowplugin.
* How to get the service and transmit raw packet to data-path.
* Define yang model and RPC interface, then receive user's input data

## packet-processing(2013-07-09)
This Rest API is flexible and powerful to deliver raw packet-data from controller through 
specific switch to network, and can be used in any software environment.
Here is a sample JSON format:
```
Url = http://<controller-ip>:8181/restconf/operations/packet-processing:transmit-packet
Method = POST
Content-type: application/json
Accept: application/json
JSON-data = {"input": {"action": [{"order": "1", "set-dl-dst-action": {"address": "00:0C:29:C8:96:6A"}}, {"order": "10", "output-action": {"output-node-connector": "openflow:11:2"}}], "connection-cookie": 123456, "egress": "/opendaylight-inventory:nodes/opendaylight-inventory:node[opendaylight-inventory:id='openflow:11']/opendaylight-inventory:node-connector[opendaylight-inventory:id='openflow:11:2']", "node": "/opendaylight-inventory:nodes/opendaylight-inventory:node[opendaylight-inventory:id='openflow:11']", "payload": "QAwpyJZqAAwp06cxCABFAAA8fR0AAEABaCjAqAoVwKgKFggATJcCAP7EYWJjZGVmZ2hpamtsbW5vcHFyc3R1dndhYmNkZWZnaGk="}}


```
Or Json request string as:
```
{
    "input": {
        "connection-cookie": 123456,
        "egress": "/opendaylight-inventory:nodes/opendaylight-inventory:node[opendaylight-inventory:id='openflow:11']/opendaylight-inventory:node-connector[opendaylight-inventory:id='openflow:11:2']",
        "node": "/opendaylight-inventory:nodes/opendaylight-inventory:node[opendaylight-inventory:id='openflow:11']",
        "payload": "QAwpyJZqAAwp06cxCABFAAA8fR0AAEABaCjAqAoVwKgKFggATJcCAP7EYWJjZGVmZ2hpamtsbW5vcHFyc3R1dndhYmNkZWZnaGk="
    }
}

```

The 'curl' command syntax for sending POST request is:
```
curl --user "admin":"admin" -H "Accept: application/json" \
-H "Content-type: application/json" -X POST \
--data "REQUEST-DATA" \
http://<IP>:8181/restconf/operations/packet-processing:transmit-packet
```


# HOW TO BUILD
In order to build the project, it's required to have JDK 1.8+ and Maven 3.2+. 
The following commands are used to build and run.
```
$ git clone https://github.com/siwind/mypacket
$ mvn clean install
$ ./karaf/target/assembly/bin/karaf 

karaf>feature:list -i | grep mypacket
karaf>log:display | grep Mypacket

```
