/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sd.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 *
 * @author jpbc
 */
public class Publisher {

    public static void main(String[] args) throws MqttException {
        String clientId = "JavaSample";
        String broker = "tcp://localhost:1883";  // URL de um Broker ativo
        String topic = "MQTT Examples";
        String topic2 = "Topico novo";
        String content = "Message from MqttPublishSample"; // mensagem a enviar
        int qos = 2;

        MqttAsyncClient sampleClient = new MqttAsyncClient(broker, clientId, new MemoryPersistence());
       
        //MqttClient sampleClient = new MqttClient(broker, clientId, new MemoryPersistence());
        MqttConnectOptions connOpts = new MqttConnectOptions();
        
        connOpts.setCleanSession(
                true);

        System.out.println(
                "Connecting to broker: " + broker);
        IMqttToken tokenConnection = sampleClient.connect(connOpts);

        System.out.println(
                "Connected");
        MqttMessage message = new MqttMessage(content.getBytes());

        message.setQos(qos);
        
        tokenConnection.waitForCompletion();
        IMqttToken token = sampleClient.publish(topic, message);
        
        IMqttToken token2 = sampleClient.publish(topic, "Teste retained".getBytes(), qos, true);
        
        
        IMqttToken token3 = sampleClient.publish(topic2, "Teste retained topic 2".getBytes(), qos, true);

        System.out.println(
                "Message published");

        sampleClient.disconnect();

    }

}
