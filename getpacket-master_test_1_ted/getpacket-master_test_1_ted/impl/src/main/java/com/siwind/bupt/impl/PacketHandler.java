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

    public PacketHandler() {
        LOG.info("[Siwind] PacketHandler Initiated. ");
    }

    @Override
    public void onPacketFeatureReply(PacketFeatureReply notification) {

        LOG.info("---------yuhao linkHandler Start---------");
        NodeConnectorRef ingress = notification.getIngress();
        java.lang.Integer deviceType = notification.getDeviceType();
        java.lang.Long deviceIp = notification.getDeviceIp();
        java.lang.Integer deviceId = notification.getDeviceId();
        java.lang.Short status1 = notification.getStatus1();
        java.lang.Short status2 = notification.getStatus2();
        java.lang.Integer waveLength = notification.getWavelength();
        java.lang.Integer targetId = notification.getTargetId();
        java.lang.Integer reserve = notification.getReserve();
        java.lang.Long compFlow = notification.getCompFlow();
        java.lang.Long totalFlow = notification.getTotalFlow();
        BigInteger connectedIdPort = notification.getConnectedIdPort();

        LOG.info("-------deviceType -------="+deviceType);
        LOG.info("-------deviceIp ---------="+deviceIp);
        LOG.info("-------deviceId ---------="+deviceId);
        LOG.info("-------status1 ----------="+status1);
        LOG.info("-------status2 ----------="+status2);
        LOG.info("-------waveLength -------="+waveLength);
        LOG.info("-------targetId ---------="+targetId);
        LOG.info("-------reserve ----------="+reserve);
        LOG.info("-------compFlow ---------= "+compFlow);
        LOG.info("-------totalFlow --------="+totalFlow);
        LOG.info("-------connectedIdPort-- ="+connectedIdPort);

        LOG.info("-------yuhao linkHandler End---------");
    }

}

