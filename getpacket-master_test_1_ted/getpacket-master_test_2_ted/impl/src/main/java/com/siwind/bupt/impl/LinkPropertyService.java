/*
 * Copyright © 2016 siwind, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package com.siwind.bupt.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeConnectorRef;

public interface LinkPropertyService {

	public Map<Long,NodeConnectorRef> getNodeMapIngress();
    
    public Set<Map<Integer,List<Long>>> getPhysicalTopo();
    
    public Map<Integer,Long> getNEResource();
    
    public Map<Integer,Long> getNodeMapIp();

}