package net.munki.play.las2peer;

import i5.las2peer.api.p2p.ServiceNameVersion;
import i5.las2peer.p2p.LocalNode;
import i5.las2peer.p2p.LocalNodeManager;
import i5.las2peer.p2p.PastryNodeImpl;
import i5.las2peer.security.ServiceAgentImpl;
import i5.las2peer.security.UserAgentImpl;
import i5.las2peer.testing.MockAgentFactory;
import i5.las2peer.testing.TestSuite;
import i5.las2peer.tools.ColoredOutput;
import org.junit.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class JBotNetServiceTest {

	private static final String TEST_STORAGE_ID = "test-storage";

	private static PastryNodeImpl storageServiceNode;
	private static ServiceAgentImpl storageService;

	@BeforeClass
	public static void init() {
		try {
			System.out.println("starting test network...");
			ColoredOutput.allOff();
			// start storage service node as standalone network
			storageServiceNode = TestSuite.launchNetwork(1).get(0);
			// start storage service
			storageService = ServiceAgentImpl.createServiceAgent(
					new ServiceNameVersion(JBotNetService.class.getName(), "1.0.0"), "test-service-pass");
			storageService.unlock("test-service-pass");
			storageServiceNode.registerReceiver(storageService);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.toString());
		}
	}

	@AfterClass
	public static void stopNetwork() {
		try {
			System.out.println("stopping test network...");
			storageServiceNode.shutDown();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.toString());
		}
	}

	@Test
	public void testStorage() {
		try {
			// generate an unique identifier, use a better/safer algorithm for your own service!
			String identifier = TEST_STORAGE_ID + new Random().nextInt();
			// this is the test object that will be persisted
			JBot exampleObj = new JBot("javamunk", "javamunk", "", "warren@munki.net", "192.168.1.103", "#javamunki", "192.`68.1.208", 6777, "quit");
			storageServiceNode.invoke(storageService,
					new ServiceNameVersion(JBotNetService.class.getCanonicalName(), "1.0.0"), "storeJBot",
					new Serializable[] { identifier, exampleObj });
			// retrieve test object again from network
			JBot result = (JBot) storageServiceNode.invoke(storageService,
					new ServiceNameVersion(JBotNetService.class.getCanonicalName(), "1.0.0"), "fetchJBot",
					new Serializable[] { identifier });
			System.out.println("Success! Received test JBot with message: " + result.getMessage());
			assertEquals(exampleObj.getMessage(), result.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.toString());
		}
	}

}
