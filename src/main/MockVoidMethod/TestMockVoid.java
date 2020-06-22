package main.MockVoidMethod;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.IAnswer;
import org.junit.Before;
import org.junit.Test;

public class TestMockVoid {
	private simpleTarget mockedTarget;

	private interface simpleTarget {
		void order1();

		void order2(int x);

		public boolean equalss(Object n);
	}

	@Before
	public void setup() {
		this.mockedTarget = EasyMock.createNiceMock(simpleTarget.class);

		EasyMock.expect(this.mockedTarget.equalss(EasyMock.anyObject())).andAnswer(() -> {
			System.out.println("Invoked");
			return true;
		}).anyTimes();

		setupOrder1();
		setupOrder2();

		EasyMock.replay(this.mockedTarget);
		// mockControl.
	}

	private void setupOrder1() {
		this.mockedTarget.order1();
		EasyMock.expectLastCall().andAnswer(new IAnswer<Object>() {
			@Override
			public Object answer() {
				System.out.println("Alter: Calling");
				return null;
			}
		}).anyTimes();
	}

	private void setupOrder2() {
		Capture<Integer> captureX = EasyMock.newCapture();
		this.mockedTarget.order2(EasyMock.captureInt(captureX));
		EasyMock.expectLastCall().andAnswer(() -> {
			System.out.println("Current intput: " + captureX.getValue());
			return null;
		}).anyTimes();
	}

	@Test
	public void testOrder() {
		this.mockedTarget.order1();
		this.mockedTarget.order1();
		this.mockedTarget.order2(5);
		this.mockedTarget.order2(10);
		this.mockedTarget.equalss("d");
		// EasyMock.verify(this.mockTarget);
	}

}
