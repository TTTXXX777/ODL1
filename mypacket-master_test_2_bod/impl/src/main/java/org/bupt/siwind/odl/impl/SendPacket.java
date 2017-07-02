/*
 * Copyright © 2016 Siwind, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.bupt.siwind.odl.impl;

import java.util.concurrent.Future;
import org.opendaylight.yangtools.yang.common.RpcResult;
import org.opendaylight.yangtools.yang.common.RpcResultBuilder;

import com.siwind.bupt.impl.LinkPropertyService;
import com.siwind.bupt.impl.LinkProperty;

import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.protocol.rev130731.SetConfigInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.protocol.rev130731.SetConfigInputBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.setconfig.rev170604.PacketSetConfigService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.setconfig.rev170604.PacketSetConfigInputBuilder;

import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.nodes.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeRef;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.bupt.siwind.odl.impl.InstanceIdentifierUtils;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeConnectorRef;

import java.util.*;
import java.lang.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SendPacket {
	private static final Logger LOG = LoggerFactory.getLogger(SendPacket.class);
	public static PacketSetConfigService packetSetConfigService = null;

	public static void sendSetConfig(int len, long ip) { 
	LOG.info("=====================sendSetConfig begin=======================");
	PacketSetConfigInputBuilder setConfigInputBuilder = new PacketSetConfigInputBuilder();

	LinkProperty linkProperty = new LinkProperty();
	
	NodeConnectorRef nodeRef = linkProperty.getNodeMapIngress().get(ip); 
	LOG.info("=====================sendSetConfig end=========================");
	InstanceIdentifier<Node> egressNodePath = InstanceIdentifierUtils.getNodePath(nodeRef.getValue());
	setConfigInputBuilder.setNode(new NodeRef(egressNodePath));
	setConfigInputBuilder.setMissSendLen(len);
	packetSetConfigService.packetSetConfig(setConfigInputBuilder.build());
	LOG.info("=====================sendbuild======================={}"+setConfigInputBuilder);
	LOG.info("=====================sendSetConfig end=========================");

	}

}