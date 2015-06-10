/***********************************************************************************
 * Smbt - A smart ambient utilities framework 
 * 
 * Copyright (c) 2008 - 2010, Lucas Bigeardel
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

package net.sf.smbt.tests.osc.outgoing;

import java.util.Random;

import net.sf.smbt.osc.utils.OscCmdUtils;
import net.sf.xqz.engine.utils.AbstractTTLJob;
import net.sf.xqz.model.engine.CmdPipe;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

/**
 * 
 * @author lucas
 *
 */
public class Md8OscBotJob extends AbstractTTLJob {
	Random random = new Random();
	private CmdPipe md8Engine;
	public Md8OscBotJob(CmdPipe engine) {
		super(3600000); // 1 hour
		md8Engine = engine;
	}
	@Override
	protected IStatus run(IProgressMonitor monitor) {
		// Md8OscController.INSTANCE.sendRandomFadeABVal(md8Engine);
		OscCmdUtils.INSTANCE.sendRandomOSCCmd(md8Engine);
		schedule(random.nextInt(50));
		return Status.OK_STATUS;
	}
}
