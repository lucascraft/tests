/***********************************************************************************
 * Smbt - A smart ambient utilities framework 
 * 
 * Copyright (c) 2010, Lucas Bigeardel
 * 
 * The library is released under:
 * 
 * A) LGPL
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General
 * Public License along with this library; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
 * Boston, MA 02110-1301 USA
 * 
 * B) EPL
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Lucas Bigeardel <lucas.bigeardel@gmail.com> - Initial API and implementation
 ***********************************************************************************/

package net.sf.smbt.tests.osc.ezmojo;

import junit.framework.TestCase;
import net.sf.smbt.ezmojo.EZMojoNode;
import net.sf.smbt.osc.ezmojo.utils.EzMojoCmdUtils;

import com.illposed.osc.OSCMessage;

public class SimpleInsert1TestCase extends TestCase {

	public void testInsertOSCMessage() {
		EZMojoNode root = EzMojoCmdUtils.INSTANCE.createEzMojoNode("/");
		
		OSCMessage m1 = new OSCMessage("/t1/t2/t3", new Object[] { new Integer(23), new String("foo"), new Float(3.9f)});
		OSCMessage m2 = new OSCMessage("/t1/t4/t31", new Object[] { new Integer(23), new String("foo"), new Float(3.9f)});
		OSCMessage m3 = new OSCMessage("/t1/t5/t33", new Object[] { new Integer(23), new String("foo"), new Float(3.9f)});
		OSCMessage m4 = new OSCMessage("/t1/t7/t34", new Object[] { new Integer(23), new String("foo"), new Float(3.9f)});
		OSCMessage m5 = new OSCMessage("/t1/t8/t35", new Object[] { new Integer(23), new String("foo"), new Float(3.9f)});
		OSCMessage m6 = new OSCMessage("/t1/t22/t83", new Object[] { new Integer(23), new String("foo"), new Float(3.9f)});

		EzMojoCmdUtils.INSTANCE.setMojo(
			root, 
			new OSCMessage[] { 
				m1, m2, m3, m4, m5, m6
			}
		);
	}
}
