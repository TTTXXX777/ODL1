/*
 * Copyright © 2016 siwind, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package com.siwind.bupt.impl;

import java.util.Arrays;

import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.featurereply.rev170515.PacketFeatureReply;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.featurereply.rev170515.PacketFeatureReplyListener;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeConnectorRef;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigInteger;


public class PacketHandler implements PacketFeatureReplyListener {

    private static final Logger LOG = LoggerFactory.getLogger(PacketHandler.class);

    public LinkProperty linkProperty = new LinkProperty();

    public PacketHandler() {
        LOG.info("[Siwind] PacketHandler Initiated. ");
    }

    @Override
    public void onPacketFeatureReply(PacketFeatureReply notification) {

        LOG.info("=======tianxiang PacketHandler onPacketFeatureReply-{}",notification);
        linkProperty.linkHandler(notification);
        linkProperty.display();       
    }

}

