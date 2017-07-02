/*
 * Copyright © 2016 siwind, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package com.siwind.bupt.impl;

import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeConnectorRef;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.featurereply.rev170515.PacketFeatureReply;

import java.util.Map;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.math.BigInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LinkProperty implements LinkPropertyService {
    private static final Logger LOG = LoggerFactory.getLogger(LinkProperty.class);

    private static Set<Map<Integer,List<Long>>> physicalTopo = new HashSet<Map<Integer,List<Long>>>();
    private static Map<Long,NodeConnectorRef> nodeMapIngress = new HashMap<Long,NodeConnectorRef>();
    private static Map<Integer,Long> nodeMapIp = new HashMap<Integer,Long>();

    private static Map<Integer,Long> ne_Resource = new HashMap<Integer,Long>();

    public LinkProperty() {}

    public void linkHandler(PacketFeatureReply replyBody) {
        
        LOG.info("------nelinkHandler begin----------{}",replyBody);
        NodeConnectorRef ingress = replyBody.getIngress();
        LOG.info("------neingress ----------{}",ingress);
        java.lang.Long device_IP = replyBody.getDeviceIp();
        LOG.info("------neip---------{}"+device_IP);
        java.lang.Integer device_ID = replyBody.getDeviceId();
        LOG.info("---------------------------------neid--------------------------------------{}"+device_ID);
        java.lang.Short switch_status = replyBody.getStatus1();
        nodeMapIngress.put(device_IP,ingress);
        nodeMapIp.put(device_ID,device_IP);

        java.lang.Long temp1 = (replyBody.getConnectedIdPort()).longValue();
        java.lang.Long ne_A1_port = temp1 & 0x0000000f;
        java.lang.Long ne_A1_id = (temp1 >> 4) & 0x0000000f;
        java.lang.Long ne_A2_port = (temp1 >> 8) & 0x0000000f;
        java.lang.Long ne_A2_id = (temp1 >> 12) & 0x0000000f;
        java.lang.Long ne_L1_port = (temp1 >> 16) & 0x0000000f;
        java.lang.Long ne_L1_id = (temp1 >> 20) & 0x0000000f;
        java.lang.Long ne_L2_port = (temp1 >> 24) & 0x0000000f;
        java.lang.Long ne_L2_id = (temp1 >> 28) & 0x0000000f;

        if(ne_A1_id > device_ID){
            List<Long> link = new ArrayList<Long>();
            link.add(1L);
            link.add(ne_A1_id);
            link.add(ne_A1_port);
            link.add(10L);
            link.add(1550L);
            Map<Integer,List<Long>> physicalLinks =  new HashMap<Integer,List<Long>>();
            physicalLinks.put(device_ID,link);
            physicalTopo.add(physicalLinks);
        }
        if(ne_A2_id > device_ID){
            List<Long> link = new ArrayList<Long>();
            link.add(2L);
            link.add(ne_A2_id);
            link.add(ne_A2_port);
            link.add(10L);
            link.add(1550L);
            Map<Integer,List<Long>> physicalLinks =  new HashMap<Integer,List<Long>>();
            physicalLinks.put(device_ID,link);
            physicalTopo.add(physicalLinks);
        }
        if(ne_L1_id > device_ID){
            List<Long> link = new ArrayList<Long>();
            link.add(3L);
            link.add(ne_L1_id);
            link.add(ne_L1_port);
            link.add(10L);
            link.add(1550L);
            Map<Integer,List<Long>> physicalLinks =  new HashMap<Integer,List<Long>>();
            physicalLinks.put(device_ID,link);
            physicalTopo.add(physicalLinks);
        }
        if(ne_L2_id > device_ID){
            List<Long> link = new ArrayList<Long>();
            link.add(4L);
            link.add(ne_L2_id);
            link.add(ne_L2_port);
            link.add(10L);
            link.add(1550L);
            Map<Integer,List<Long>> physicalLinks =  new HashMap<Integer,List<Long>>();
            physicalLinks.put(device_ID,link);
            physicalTopo.add(physicalLinks);
        }
   
        ne_Resource.put(device_ID,63L);
        
        LOG.info("------nelinkHandler end----------");  
    }

    public void display() {
    	LOG.info("------physicalTopo display----------{}"+physicalTopo);
        LOG.info("------physicalTopo size display----------{}"+physicalTopo.size());
        LOG.info("------nodeMapIngress display----------{}"+nodeMapIp);
        LOG.info("------ne_Resource display----------{}"+ne_Resource.size());
    }

    @Override
    public Map<Long,NodeConnectorRef> getNodeMapIngress(){
        LOG.info("=========aoniTEd linkproperty:nodeMapIngress====={}");
        
        return nodeMapIngress;
    }
    public Map<Integer,Long> getNodeMapIp(){
        return nodeMapIp;
    }
    public Set<Map<Integer,List<Long>>> getPhysicalTopo(){
        return physicalTopo;
    }
    
    public Map<Integer,Long> getNEResource() {
        return ne_Resource;
    }
    
}
