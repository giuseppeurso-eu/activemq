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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;


/**
 * The messages consumer. It receives the source file and makes a copy into the destination directory.
 * 
 * @author Giuseppe Urso
 *
 */
public class ConsumerProcessor implements Processor {

	
	/**
	 * The method used to implement consumers of message exchanges.
	 */
	@Override
	public void process(Exchange exchange) throws Exception {
		
		try {
			Message message = exchange.getIn();
			File fileToProcess = message.getBody(File.class);
			System.out.println("Detected file name '" + fileToProcess.getAbsolutePath() + "'. Trying to copy in: "+ConsumerRouteBuilder.getDestFolder());
			FileInputStream newFileIs = new FileInputStream(fileToProcess);
			String destDir = ConsumerRouteBuilder.getDestFolder();
			checkFolder(destDir);
			File destFile = new File(destDir+File.separator+fileToProcess.getName());
			Files.copy(newFileIs, destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			System.out.println("File copied in: "+ destDir+File.separator+fileToProcess.getName());
			if (newFileIs != null) {
				newFileIs.close();
			}
		} catch (Exception e) {
			System.err.println("Exception while trying to import invoce: " + e);
		} 
	}

	/**
	 * Checks the folder path and creates it if not exists.
	 * @param folder
	 * @throws Exception
	 */
	public static void checkFolder(String folder) throws Exception {
		File folderF = new File(folder);
		if (!folderF.exists() || !folderF.isDirectory()) {
			if (folderF.mkdirs()) {
				System.out.println("Directory is created: " + folder);
			} else {
				System.out.println("Failed to create directory: " + folder);
				throw new IOException("Failed to create directory: '" + folder+ "'. Check if the specified path exists and is read/writable.");
			}
		} else if (!folderF.canRead() || !folderF.canWrite()) {
			System.out.println("Check directory permission: " + folder);
			throw new IOException(
					"Unable to access the directory: '" + folder + "'. Check if the specified path is read/writable.");
		}
	}
}
