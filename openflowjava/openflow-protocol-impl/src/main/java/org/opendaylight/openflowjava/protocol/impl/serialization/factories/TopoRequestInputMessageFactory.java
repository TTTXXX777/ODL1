/*
 * Copyright (c) 2013 Pantheon Technologies s.r.o. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.openflowjava.protocol.impl.serialization.factories;

import io.netty.buffer.ByteBuf;

import org.opendaylight.openflowjava.protocol.api.extensibility.OFSerializer;
import org.opendaylight.openflowjava.protocol.api.extensibility.SerializerRegistry;
import org.opendaylight.openflowjava.protocol.api.extensibility.SerializerRegistryInjector;
import org.opendaylight.openflowjava.protocol.api.util.EncodeConstants;
import org.opendaylight.openflowjava.protocol.impl.util.ListSerializer;
import org.opendaylight.openflowjava.protocol.impl.util.TypeKeyMakerFactory;
import org.opendaylight.openflowjava.util.ByteBufUtils;

import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.protocol.rev130731.TopoRequestInput;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Translates TopoRequest messages
 * @author michal.polkorab
 * @author timotej.kubas
 */
public class TopoRequestInputMessageFactory implements OFSerializer<TopoRequestInput> {

    /** Code type of PacketOut message */
    private static final Logger LOG = LoggerFactory.getLogger(TopoRequestInputMessageFactory.class);

    private static final byte MESSAGE_TYPE = 30;

    @Override
    public void serialize(TopoRequestInput message, ByteBuf outBuffer) {
        ByteBufUtils.writeOFHeader(MESSAGE_TYPE, message, outBuffer, EncodeConstants.EMPTY_LENGTH);
        outBuffer.writeInt(message.getExpType().intValue());
        
        ByteBufUtils.updateOFHeaderLength(outBuffer);
    }

}
