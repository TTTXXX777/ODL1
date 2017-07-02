/*
 * Copyright © 2016 Siwind, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.bupt.siwind.odl.impl;

// import org.osgi.framework.Bundle; //added for communications between bundles;
// import org.osgi.framework.BundleContext; //added for communications between bundles;
// import org.osgi.framework.BundleReference; //added for communications between bundles;
// import org.osgi.framework.FrameworkUtil;//added for communications between bundles;
// import org.osgi.framework.ServiceReference; //added for communications between bundles;

import java.util.concurrent.Future;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.mypacket.rev150105.SendPacketInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.mypacket.rev150105.SendPacketOutput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.mypacket.rev150105.SendPacketOutputBuilder;
import org.opendaylight.yangtools.yang.common.RpcResult;
import org.opendaylight.yangtools.yang.common.RpcResultBuilder;

import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.mypacket.rev150105.MypacketService;


// import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.TransmitPacketInput;
// import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.TransmitPacketInputBuilder;

// //add for set-config
// import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.setconfig.rev170604.PacketSetConfigService;

// import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.setconfig.rev170604.PacketSetConfigInput;
// import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.setconfig.rev170604.PacketSetConfigInputBuilder;

//add for ted
import com.siwind.bupt.impl.LinkProperty;
import com.siwind.bupt.impl.LinkPropertyService;

// import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.types.rev130731.SwitchConfigFlag;

// import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;

// import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.node.NodeConnector;
// import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeConnectorRef;
// import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeRef;
// import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.nodes.Node;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MypacketImpl implements MypacketService {

	private static final Logger LOG = LoggerFactory.getLogger(MypacketImpl.class);
	
	//private final PacketSetConfigService packetSetConfigService;
	private final LinkPropertyService linkPropertyService;

	/**
	 *
	 * @param linkPropertyService
	 */
	public MypacketImpl(LinkPropertyService linkPropertyService) {
		this.linkPropertyService = linkPropertyService;
	}

	// private static BundleContext getBundleContext() {
 //        ClassLoader tlcl = Thread.currentThread().getContextClassLoader();
 //        Bundle bundle = null;

 //        if (tlcl instanceof BundleReference) {
 //            bundle = ((BundleReference) tlcl).getBundle();
 //        } else {
 //            LOG.info("Unable to determine the bundle context based on thread context classloader.");
 //            bundle = FrameworkUtil.getBundle(MypacketImpl.class);
 //        }
 //        return (bundle == null ? null : bundle.getBundleContext());
 //    }

	@Override
	public Future<RpcResult<SendPacketOutput>> sendPacket(SendPacketInput input) {
		SendPacketOutputBuilder helloBuilder = new SendPacketOutputBuilder();
		
		// BundleContext ctx = getBundleContext();
  //       ServiceReference linkPropertyServiceReference = ctx.getServiceReference(LinkPropertyService.class);
  //       LinkProperty linkProperty = (LinkProperty)ctx.getService(linkPropertyServiceReference);

		// LinkProperty linkProperty = new LinkProperty();

		// NodeConnectorRef nodeRef = linkProperty.getIngress();
		
  //       InstanceIdentifier<Node> egressNodePath = getNodePath(nodeRef.getValue());
        
  //       java.lang.Integer sendlen = new Integer(1);

  //       sendSetconfig(new NodeRef(egressNodePath),SwitchConfigFlag.FRAGNORMAL,sendlen);  //send packet!!
		
		//helloBuilder.setResult("Send Packet to " + strEgress + " OK."); //return message
		String deviceId = linkPropertyService.getDeviceId1().toString();
		helloBuilder.setResult("Send Packet OK."+ input.getMisslen()+deviceId); //return message
		return RpcResultBuilder.success(helloBuilder.build()).buildFuture();
	}

	// =========================================================
	
	// private void sendSetconfig(NodeRef egressNodeRef, SwitchConfigFlag flags, java.lang.Integer sendlen) {
        
 //        LOG.info("TIANXIANG Send packet of size {} out of port {}");

 //        //Construct input for RPC call to packet processing service
 //        PacketSetConfigInput input = new PacketSetConfigInputBuilder()
 //                .setFlags(flags)
 //                .setNode(egressNodeRef)
 //                .setMissSendLen(sendlen)
 //                .build();
 //        packetSetConfigService.packetSetConfig(input);       
 //    }    

	// private InstanceIdentifier<Node> getNodePath(final InstanceIdentifier<?> nodeChild) {
	// 	return nodeChild.firstIdentifierOf(Node.class);
	// }
}