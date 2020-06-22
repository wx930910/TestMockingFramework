package test.testPowerMock;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.mockito.Answers;
import org.mockito.Mockito;

abstract class AbstractMessage {
	private final String message;

	AbstractMessage() {
		this.message = "hey!";
	}

	String getMessage() {
		return message;
	}
}

abstract class AbstarctCalculator {

	private int a;
	private int b;

	public AbstarctCalculator(int a, int b) {
		System.out.println("Invoking constructor1");
		this.a = a;
		this.b = b;
	}

	public int calculate() {
		System.out.println("Invoking calculate function in parent class");
		return a + b;
	}

}

public class TestMockingConstructor {
	public static abstract class calculator {
		private int a;
		private int b;

		public calculator(int a, int b) {
			System.out.println("Invoking constructor1");
			this.a = a;
			this.b = b;
		}

		public calculator() {
			System.out.println("Invoking constructor2");
		}

		public int add() {
			System.out.println("Invoking add method");
			System.out.println(this.a);
			return this.a + this.b;
		}

	}

	@Test
	public void testPowerMockingConstructor1() throws Exception {
		int a = 5, b = 10;
		AbstarctCalculator instance = Mockito.mock(AbstarctCalculator.class,
				Mockito.withSettings().useConstructor(a, b)
						.defaultAnswer(Answers.CALLS_REAL_METHODS));
		Mockito.doAnswer(invocation -> {
			invocation.callRealMethod();
			System.out.println("Invoking mocking methods");
			return a + b;
		}).when(instance).calculate();
		instance.calculate();
	}

	@Test
	public void can_mock_abstract_classes() {
		AbstractMessage mock = Mockito.mock(AbstractMessage.class,
				Mockito.withSettings().useConstructor()
						.defaultAnswer(Mockito.CALLS_REAL_METHODS));
		assertEquals("hey!", mock.getMessage());
	}

}
