/*
 * Copyright © 2016 Siwind, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.bupt.siwind.odl.impl;

import java.util.concurrent.Future;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.mypacket.rev150105.SendMypacketInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.mypacket.rev150105.SendMypacketOutput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.mypacket.rev150105.SendMypacketOutputBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.mypacket.rev150105.MypacketService;
import org.opendaylight.yangtools.yang.common.RpcResult;
import org.opendaylight.yangtools.yang.common.RpcResultBuilder;



// import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.PacketProcessingService;

// import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.TransmitPacketInput;
// import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.TransmitPacketInputBuilder;

// import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;

// import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.node.NodeConnector;
// import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeConnectorRef;
// import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeRef;
// import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.nodes.Node;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MypacketImpl implements MypacketService {
	private final Logger LOD = LoggerFactory.getLogger(this.getClass());

	@Override 
	public Future<RpcResult<SendMypacketOutput>> sendMypacket(SendMypacketInput input) {
		SendMypacketOutputBuilder helloBuilder = new SendMypacketOutputBuilder();
			SendPacket.sendSetConfig(10,20);
			helloBuilder.setResult("Hello " + input.getName());
			return RpcResultBuilder.success(helloBuilder.build()).buildFuture();

	}
}