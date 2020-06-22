package test.MockReturn;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class TestMockReturn {
	private simpleTarget mockedTarget;

	private interface simpleTarget {
		int order1();

		void order2(int x);

		boolean equals(Object n);
	}

	@Before
	public void setup() {
		this.mockedTarget = Mockito.mock(simpleTarget.class);
		Mockito.when(this.mockedTarget.order1()).thenAnswer(new Answer<Integer>() {
			@Override
			public Integer answer(InvocationOnMock invocation) {
				System.out.println("Invoked");
				return 10;
			}
		});

		// Mockito.
	}

	@Test
	public void testOrder() {
		System.out.println(this.mockedTarget.order1());

	}

}
