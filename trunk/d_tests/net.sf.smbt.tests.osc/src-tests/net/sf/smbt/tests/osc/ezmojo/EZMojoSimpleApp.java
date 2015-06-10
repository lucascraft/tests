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

import net.sf.smbt.ezmojo.EZMojoNode;
import net.sf.smbt.osc.ezmojo.utils.EzMojoCmdUtils;
import net.sf.xqz.engine.utils.TTLApplication;

import org.eclipse.equinox.app.IApplicationContext;

import com.illposed.osc.OSCMessage;

public class EZMojoSimpleApp extends TTLApplication {

	@Override
	public Object start(IApplicationContext context) throws Exception {
		EZMojoNode root = EzMojoCmdUtils.INSTANCE.createEzMojoNode("/");
		
		OSCMessage mi0 = new OSCMessage("/t0", new Object[] { new Integer(23), new String("foo"), new Float(3.9f)});
		OSCMessage mi1 = new OSCMessage("/t1/t2/t3", new Object[] { new Integer(23), new String("foo"), new Float(3.9f)});
		OSCMessage mi2 = new OSCMessage("/t1/t4/t31", new Object[] { new Integer(23), new String("foo"), new Float(3.9f)});
		OSCMessage mi3 = new OSCMessage("/t1/t5/t33", new Object[] { new Integer(23), new String("foo"), new Float(3.9f)});
		OSCMessage mi4 = new OSCMessage("/t1/t7/t34", new Object[] { new Integer(23), new String("foo"), new Float(3.9f)});
		OSCMessage mi5 = new OSCMessage("/t1/t8/t35", new Object[] { new Integer(23), new String("foo"), new Float(3.9f)});
		OSCMessage mi6 = new OSCMessage("/t1/t22/t83", new Object[] { new Integer(23), new String("foo"), new Float(3.9f)});

		OSCMessage mx0 = new OSCMessage("/t0", new Object[] { new Integer(32), new String("bar"), new Float(3.5f)});
		OSCMessage mx1 = new OSCMessage("/t1/t2/t3", new Object[] { new Integer(32), new Float(3.3f)});
		OSCMessage mx2 = new OSCMessage("/t1/t4/t31", new Object[] { new Integer(32), new String("bar"), new Float(3.5f), new String("dude"), new String("well")});
		OSCMessage mx3 = new OSCMessage("/t1/t5/t33", new Object[] { new Integer(32), new String("foo"), new Float(3.6f)});
		OSCMessage mx4 = new OSCMessage("/t1/t7/t34", new Object[] { new Integer(32), new String("foo")});
		OSCMessage mx5 = new OSCMessage("/t1/t8/t35", new Object[] { new Integer(32), new String("foo"), new Float(3.8f)});
		OSCMessage mx6 = new OSCMessage("/t1/t22/t83", new Object[] { new String("foo"), new Float(1.9f)});

		EzMojoCmdUtils.INSTANCE.setMojo(
			root, 
			new OSCMessage[] { 
			    mi0, 
			    mi1, 
			    mi2, 
			    mi3, 
			    mi4, 
			    mi5, 
			    mi6
			}
		);
		
		/* EZMojoNode n0 =*/ EzMojoCmdUtils.INSTANCE.findMojo(root, mi0.getAddress());
		/* EZMojoNode n1 =*/ EzMojoCmdUtils.INSTANCE.findMojo(root, mi1.getAddress());
		/* EZMojoNode n2 =*/ EzMojoCmdUtils.INSTANCE.findMojo(root, mi2.getAddress());
		/* EZMojoNode n3 =*/ EzMojoCmdUtils.INSTANCE.findMojo(root, mi3.getAddress());
		/* EZMojoNode n4 =*/ EzMojoCmdUtils.INSTANCE.findMojo(root, mi4.getAddress());
		/* EZMojoNode n5 =*/ EzMojoCmdUtils.INSTANCE.findMojo(root, mi5.getAddress());
		/* EZMojoNode n6 =*/ EzMojoCmdUtils.INSTANCE.findMojo(root, mi6.getAddress());

		EzMojoCmdUtils.INSTANCE.updateMojo(root, mx0);
		EzMojoCmdUtils.INSTANCE.updateMojo(root, mx1);
		EzMojoCmdUtils.INSTANCE.updateMojo(root, mx2);
		EzMojoCmdUtils.INSTANCE.updateMojo(root, mx3);
		EzMojoCmdUtils.INSTANCE.updateMojo(root, mx4);
		EzMojoCmdUtils.INSTANCE.updateMojo(root, mx5);
		EzMojoCmdUtils.INSTANCE.updateMojo(root, mx6);
		
		for (OSCMessage m : EzMojoCmdUtils.INSTANCE.getOSCMessagesFromEzMojoNode(root)) {
			System.out.println(m.getAddress() + " : " + m.toString() );
		}
		
		return super.start(context);
	}

	@Override
	public void stop() {

	}

}
