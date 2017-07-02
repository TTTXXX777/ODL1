/*
 * Copyright (c) 2013 Pantheon Technologies s.r.o. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.openflowjava.protocol.impl.deserialization.factories;

import io.netty.buffer.ByteBuf;

import java.math.BigInteger;

import org.opendaylight.openflowjava.protocol.api.extensibility.OFDeserializer;
import org.opendaylight.openflowjava.protocol.api.extensibility.DeserializerRegistry;
import org.opendaylight.openflowjava.protocol.api.extensibility.DeserializerRegistryInjector;
import org.opendaylight.openflowjava.protocol.api.util.EncodeConstants;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.types.rev130731.Capabilities;


import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.protocol.rev130731.FeatureReplyMessage;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.protocol.rev130731.FeatureReplyMessageBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Translates FeaturesReply messages
 * @author michal.polkorab
 * @author timotej.kubas
 */
public class IrofFeaturesReplyMessageFactory implements OFDeserializer<FeatureReplyMessage>, DeserializerRegistryInjector{

    private static final byte PADDING_IN_FEATURES_REPLY_HEADER = 2;

    private static final Logger LOG = LoggerFactory.getLogger(IrofFeaturesReplyMessageFactory.class);


    private DeserializerRegistry registry;

    @Override
    public FeatureReplyMessage deserialize(ByteBuf rawMessage) {

        LOG.info("=================IrofFeaturesReplyMessageFactory begin=========================");
        FeatureReplyMessageBuilder builder = new FeatureReplyMessageBuilder();
        builder.setVersion((short) EncodeConstants.OF13_VERSION_ID);
        builder.setXid(rawMessage.readUnsignedInt());
        // byte[] datapathId = new byte[EncodeConstants.SIZE_OF_LONG_IN_BYTES];
        // rawMessage.readBytes(datapathId);
        // builder.setDatapathId(new BigInteger(1, datapathId));
    
        // LOG.info("=================IrofFeaturesReplyMessageFactory datapathId============"+datapathId);
        // builder.setBuffers(rawMessage.readUnsignedInt());
        // builder.setTables(rawMessage.readUnsignedByte());
        // builder.setAuxiliaryId(rawMessage.readUnsignedByte());
        // rawMessage.skipBytes(PADDING_IN_FEATURES_REPLY_HEADER);
        // builder.setCapabilities(createCapabilities(rawMessage.readUnsignedInt()));
        // builder.setReserved(rawMessage.readUnsignedInt());
        //IROF yuhao
        builder.setDeviceType(rawMessage.readUnsignedShort());
        LOG.info("=================IrofFeaturesReplyMessageFactory DeviceType============");
        builder.setDeviceId(rawMessage.readUnsignedShort());
        builder.setDeviceIp(rawMessage.readUnsignedInt());
        builder.setStatus1(rawMessage.readUnsignedByte());
        builder.setStatus2(rawMessage.readUnsignedByte());
        builder.setWavelength(rawMessage.readUnsignedShort());
        builder.setTargetId(rawMessage.readUnsignedShort());
        builder.setReserve(rawMessage.readUnsignedShort());
        builder.setCompFlow(rawMessage.readUnsignedInt());
        builder.setTotalFlow(rawMessage.readUnsignedInt());
        byte[] connectedidport = new byte[EncodeConstants.SIZE_OF_LONG_IN_BYTES];
        rawMessage.readBytes(connectedidport);
        builder.setConnectedIdPort(new BigInteger(1, connectedidport));
        
        return builder.build();
    }

    private static Capabilities createCapabilities(long input) {
        final Boolean flowStats = (input & (1 << 0)) != 0;
        final Boolean tableStats = (input & (1 << 1)) != 0;
        final Boolean portStats = (input & (1 << 2)) != 0;
        final Boolean groupStats = (input & (1 << 3)) != 0;
        final Boolean ipReasm = (input & (1 << 5)) != 0;
        final Boolean queueStats = (input & (1 << 6)) != 0;
        final Boolean portBlocked = (input & (1 << 8)) != 0;
        return new Capabilities(flowStats, groupStats, ipReasm,
                portBlocked, portStats, queueStats, tableStats);
    }

    @Override
    public void injectDeserializerRegistry(DeserializerRegistry deserializerRegistry) {
        registry = deserializerRegistry;
    }

}
