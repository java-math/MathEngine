package uk.co.ryanharrison.mathengine.parser.nodes;

import uk.co.ryanharrison.mathengine.Utils;
import uk.co.ryanharrison.mathengine.parser.operators.Determinable;

public final class NodeVector extends NodeConstant
{
	private Node[] values;

	public NodeVector(Node[] values)
	{
		this.values = values;
	}

	public NodeVector(uk.co.ryanharrison.mathengine.linearalgebra.Vector v)
	{
		values = new NodeDouble[v.getSize()];

		for (int i = 0; i < values.length; i++)
		{
			values[i] = new NodeDouble(v.get(i));
		}
	}

	public NodeVector(Vector v)
	{
		values = new Node[v.getSize()];

		for (int i = 0; i < values.length; i++)
		{
			values[i] = v.get(i).clone();
		}
	}

	@Override
	public NodeConstant add(NodeMatrix arg2)
	{
		return new NodeMatrix(new Matrix(toVector()).add(arg2.toMatrix()));
	}

	@Override
	public NodeConstant add(final NodeNumber arg2)
	{
		return applyDeterminable(new Determinable()
		{
			@Override
			public NodeNumber getResult(NodeConstant number)
			{
				return number.add(arg2).getTransformer().toNodeNumber();
			}
		});
	}

	@Override
	public NodeConstant add(final NodePercent arg2)
	{
		return applyDeterminable(new Determinable()
		{
			@Override
			public NodeNumber getResult(NodeConstant number)
			{
				return number.add(arg2).getTransformer().toNodeNumber();
			}
		});
	}

	@Override
	public NodeConstant add(NodeVector arg2)
	{
		return new NodeVector(toVector().add(arg2.toVector()));
	}

	@Override
	public NodeVector applyDeterminable(Determinable deter)
	{
		NodeConstant[] result = new NodeConstant[values.length];

		for (int i = 0; i < values.length; i++)
		{
			if (values[i] instanceof NodeNumber)
			{
				result[i] = deter.getResult((NodeNumber) values[i]);
			}
			else
			{
				result[i] = ((NodeConstant) values[i]).applyDeterminable(deter);
			}
		}

		return new NodeVector(result);
	}

	@Override
	public int compareTo(NodeConstant cons)
	{
		double sum = toVector().sum().doubleValue();

		if (cons instanceof NodeVector)
		{
			return Double.compare(sum, ((NodeVector) cons).toDoubleVector()
					.sum());
		}
		else
		{
			return new NodeDouble(sum).compareTo(cons);
		}
	}

	@Override
	public NodeConstant divide(NodeMatrix arg2)
	{
		return new NodeMatrix(
				new Matrix(toVector()).divide(arg2.toMatrix()));
	}

	@Override
	public NodeConstant divide(NodeNumber arg2)
	{
		return new NodeVector(toVector().divide(arg2).getElements());
	}

	@Override
	public NodeConstant divide(final NodePercent arg2)
	{
		return applyDeterminable(new Determinable()
		{
			@Override
			public NodeNumber getResult(NodeConstant number)
			{
				return number.divide(arg2).getTransformer().toNodeNumber();
			}
		});
	}

	@Override
	public NodeConstant divide(NodeVector arg2)
	{
		return new NodeVector(toVector().divide(arg2.toVector()));
	}

	@Override
	public boolean equals(Object object)
	{
		if (object instanceof NodeVector)
		{
			return this.toDoubleVector().equals(
					((NodeVector) object).toDoubleVector());
		}

		return false;
	}

	public int getSize()
	{
		return values.length;
	}

	public Node[] getValues()
	{
		return values;
	}

	@Override
	public int hashCode()
	{
		return values.hashCode();
	}

	public boolean isAllDoubles()
	{
		for (Node c : values)
		{
			if (!(c instanceof NodeNumber))
				return false;
		}

		return true;
	}

	@Override
	public NodeConstant multiply(NodeMatrix arg2)
	{
		return new NodeMatrix(new Matrix(toVector()).multiply(arg2
				.toMatrix()));
	}

	@Override
	public NodeConstant multiply(NodeNumber arg2)
	{
		return new NodeVector(toVector().multiply(arg2).getElements());
	}

	@Override
	public NodeConstant multiply(final NodePercent arg2)
	{
		return applyDeterminable(new Determinable()
		{
			@Override
			public NodeNumber getResult(NodeConstant number)
			{
				return number.multiply(arg2).getTransformer().toNodeNumber();
			}
		});
	}

	@Override
	public NodeConstant multiply(NodeVector arg2)
	{
		return new NodeVector(toVector().multiply(arg2.toVector()));
	}

	@Override
	public NodeConstant pow(NodeMatrix arg2)
	{
		return new NodeMatrix(new Matrix(toVector()).pow(arg2.toMatrix()));
	}

	@Override
	public NodeConstant pow(NodeNumber arg2)
	{
		return new NodeVector(toVector().pow(arg2).getElements());
	}

	@Override
	public NodeConstant pow(final NodePercent arg2)
	{
		return applyDeterminable(new Determinable()
		{
			@Override
			public NodeNumber getResult(NodeConstant number)
			{
				return number.pow(arg2).getTransformer().toNodeNumber();
			}
		});
	}

	@Override
	public NodeConstant pow(NodeVector arg2)
	{
		return new NodeVector(toVector().pow(arg2.toVector()));
	}

	public void setValue(NodeConstant[] values)
	{
		this.values = values;
	}

	@Override
	public NodeConstant subtract(NodeMatrix arg2)
	{
		return new NodeMatrix(new Matrix(toVector()).subtract(arg2
				.toMatrix()));
	}

	@Override
	public NodeConstant subtract(NodeNumber arg2)
	{
		return new NodeVector(toVector().subtract(arg2).getElements());
	}

	@Override
	public NodeConstant subtract(final NodePercent arg2)
	{
		return applyDeterminable(new Determinable()
		{
			@Override
			public NodeNumber getResult(NodeConstant number)
			{
				return number.subtract(arg2).getTransformer().toNodeNumber();
			}
		});
	}

	@Override
	public NodeConstant subtract(NodeVector arg2)
	{
		return new NodeVector(toVector().subtract(arg2.toVector()));
	}

	public uk.co.ryanharrison.mathengine.linearalgebra.Vector toDoubleVector()
	{
		NodeNumber[] a = toVector().getElements();

		double[] v = new double[a.length];

		for (int i = 0; i < v.length; i++)
		{
			v[i] = a[i].doubleValue();
		}

		return new uk.co.ryanharrison.mathengine.linearalgebra.Vector(v);
	}

	public Vector toVector()
	{
		NodeNumber[] results = new NodeNumber[values.length];
		for (int i = 0; i < results.length; i++)
			results[i] = values[i].getTransformer().toNodeNumber();

		return new Vector(results);
	}

	@Override
	public String toString()
	{
		return "{ " + Utils.join(values, ", ") + " }";
	}

	@Override
	public String toTypeString()
	{
		return "vector";
	}

	@Override
	public NodeTransformer getTransformer()
	{
		if (this.transformer == null)
			this.transformer = new NodeVectorTransformer();

		return this.transformer;
	}

	private class NodeVectorTransformer implements NodeTransformer
	{

		@Override
		public NodeVector toNodeVector()
		{
			return NodeVector.this;
		}

		@Override
		public NodeNumber toNodeNumber()
		{
			return toVector().sum();
		}
	}
}
