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

package net.sf.smbt.tests.firmata.ubiquino.testscases;

import java.util.Random;

import net.sf.smbt.mdl.arduino.AnalogPort;
import net.sf.smbt.mdl.arduino.Arduino;
import net.sf.smbt.mdl.arduino.DigitalPort;
import net.sf.xqz.engine.utils.AbstractTTLJob;

import org.eclipse.core.runtime.Platform;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

class TimerJob extends AbstractTTLJob {
	/*
	public TimerJob() {
		this(AbstractTTLJob.FOREVER);
	}
	public TimerJob(long TTL) {
		super(UUID.randomUUID().toString(), TTL);
	}
	@Override
	protected IStatus run(IProgressMonitor monitor) {
		return Status.OK_STATUS;
	}
	*/
}

public class UbiquinoFirmataApplication implements IApplication {
	private Random randomizer;
	private Arduino diecimilaBoard;
	
	public UbiquinoFirmataApplication() {
		randomizer = new Random();
	}
	
	@Override
	public Object start(IApplicationContext context) throws Exception {
		/*
		 
		CmdPipe engine = QuanticMojo.INSTANCE.openUsbPipe("ubiquino", "/dev/tty.usbserial-A7006022", 57600);
		engine.addQxEventHandler(
			new FirmataQxEventHandler() {
				@Override
				public void handleQxEvent(Event event) {
					super.handleQxEvent(event);
				}
			}
		);

		diecimilaBoard = UbiquinoUtils.INSTANCE.createArduinoBoard(
							ARDUINO_BOARD_UID.DIECMILA_ATMEGA_168, 
							ARDUINO_FIRMWARE_MODE.ARDUINO_FIRMATA_V20, 
							engine
						);

		Cmd queryFirmwareCmd = FirmataCmdUtils.INSTANCE.createQUERY_FIRMATA_FIRMAWARE_CMD();
		engine.getTx().postCommand(queryFirmwareCmd);
		
		engine.addQxEventHandler(
				new FirmataQxEventHandler() {
					public void handleQxEvent(net.sf.xqz.model.engine.Event event) {
						super.handleQxEvent(event);
						if (event.getKind().equals(EVENT_KIND.RX_DONE)) {
							// Define here the sysex result cmd callback handling for the Arduino board model valuation
							// FIXME : create a special Firmata/Ubiquino handler (eg: EMF Adapter) for firmata board events
							// FIXME : give me input !!!
						}
					};
				}
			);

		
		AbstractTTLJob job = new TimerJob() {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				
				setAnalogValuesAtRandom(diecimilaBoard);
				
				setDigitalValuesAtRandom(diecimilaBoard);
				
				schedule(500);
				return Status.OK_STATUS;
			}
		};
		job.schedule();
		
		while (job.isRunning())
			;
		*/
		return new Object();
	}

	@Override
	public void stop() {
		
	}
	
	private void setAnalogValuesAtRandom(Arduino diecimilaBoard) {
		for (AnalogPort p : diecimilaBoard.getAnalogPorts()) {
			float value = randomizer.nextFloat();
			if (Platform.inDebugMode()) {
				System.out.println("Ubiquino : set " + p.getMap().getLiteral() + " to " + value);
			}
			p.setValue(value);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void setDigitalValuesAtRandom(Arduino diecimilaBoard) {
		for (DigitalPort p : diecimilaBoard.getDigitalPorts()) {
			int value = Math.abs(randomizer.nextInt()%2);
			if (Platform.inDebugMode()) {
				System.out.println("Ubiquino : set " + p.getMap().getLiteral() + " to " + value);
			}
			p.setValue(value);
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
