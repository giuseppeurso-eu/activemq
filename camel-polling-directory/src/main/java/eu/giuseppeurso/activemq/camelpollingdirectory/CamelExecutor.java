/**
 * This file is part of GiuseppeUrso-EU Software.
 * 
 * GiuseppeUrso-EU Software is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *             
 * GiuseppeUrso-EU Software Software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with GiuseppeUrso-EU Software.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package eu.giuseppeurso.activemq.camelpollingdirectory;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;


/**
 * The CamelContext initializer.
 * The CamelContext represents a single Camel routing rulebase. You use the CamelContext in a similar way to the Spring ApplicationContext.
 * 
 * @author Giuseppe Urso
 *
 */
public class CamelExecutor {
	
	private int waitForFile = 30000;
	private CamelContext camelCtx;
	
/**
 * The init method to retrieve the RouteBuilder and start the CamelContext for the job.
 */
	public void init() throws Exception {
		camelCtx = new DefaultCamelContext();
		try {
			camelCtx.addRoutes(new ConsumerRouteBuilder());
			if (camelCtx.getStatus().isStopped()) {
				camelCtx.start();
				System.out.println(">>>> PLEASE TRY to put a file into the source dir within "+(waitForFile/1000)+" seconds and wait processing... ");
				Thread.sleep(waitForFile);
			}
		} catch (Exception e) {
			System.out.println("Unable to initialize CamelContext: " + e);
			return;
		}
	}
}
