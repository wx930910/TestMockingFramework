package test.testStub;

import org.easymock.EasyMock;
import org.junit.Test;
import org.mockito.Mockito;

abstract class AbstractMessage {
	private String message;

	AbstractMessage() {
		this.message = "hey!";
	}

	String getMessage() {
		System.out.println("Invoking Original Method");
		System.out.println(this.message + "From Original Method");
		return message;
	}
}

public class TestMethodStub {
	@Test
	public void testMockitoStub() {
		AbstractMessage instance = Mockito.mock(AbstractMessage.class,
				Mockito.withSettings().defaultAnswer(Mockito.CALLS_REAL_METHODS)
						.useConstructor());
		Mockito.doAnswer(invocation -> {
			System.out.println("Stubbing something here");
			return invocation.callRealMethod();
		}).when(instance).getMessage();
		instance.getMessage();
		Mockito.verify(instance, Mockito.times(1)).getMessage();
	}

	@Test
	public void testEasyMockStub() {
		AbstractMessage instance = EasyMock
				.partialMockBuilder(AbstractMessage.class).withConstructor()
				.addMockedMethod("getMessage").createMock();
		EasyMock.expect(instance.getMessage()).andAnswer(() -> {
			System.out.println("Stubbing something here");
			return instance.getMessage();
		}).anyTimes();
		EasyMock.replay(instance);
		instance.getMessage();
		EasyMock.verify(instance);
	}
}
