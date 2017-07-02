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
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.featurereply.rev170515.PacketFeatureReplyListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LinkProperty implements  PacketFeatureReplyListener, LinkPropertyService {

    private static final Logger LOG = LoggerFactory.getLogger(LinkProperty.class);

    private static NodeConnectorRef ingress = null;
    private static java.lang.Integer deviceId = null;

    public LinkProperty() {
    	LOG.info("TIANXIANG linkProperty Initiated");
    }

    @Override
    public void onPacketFeatureReply(PacketFeatureReply notification) {

        LOG.info("=======TIANXIANG onPacketFeatureReply start================");
        ingress = notification.getIngress();
        deviceId = notification.getDeviceId();
        LOG.info("==========TIANXIANG onPacketFeatureReply ingress ="+ingress); 
        LOG.info("==========TIANXIANG onPacketFeatureReply deviceId="+deviceId);    
    }


    public NodeConnectorRef getIngress() {
    	return ingress;
    }


	public java.lang.Integer getDeviceId() {
		return deviceId;
	}

    @Override
    public java.lang.Integer getDeviceId1() {
        return deviceId;
    }
}

