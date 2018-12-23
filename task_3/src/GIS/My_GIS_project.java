package GIS;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * 
 * @author Shilo Gilor and Amiel Liberman
 *
 */
public class My_GIS_project implements GIS_project{

    Set<GIS_layer> my_layers = new HashSet<GIS_layer>();
	private My_meta_data data;
	


	public My_GIS_project(My_meta_data data) {
		super();
		this.data = data;
	}

	@Override
	public int size() {
		return my_layers.size();
	}

	@Override
	public boolean isEmpty() {
		return my_layers.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return my_layers.contains(o);
	}

	@Override
	public Iterator<GIS_layer> iterator() {
		return my_layers.iterator();
	}

	@Override
	public Object[] toArray() {
		return my_layers.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return my_layers.toArray(a);
	}

	@Override
	public boolean add(GIS_layer e) {
		return my_layers.add(e);
	}

	@Override
	public boolean remove(Object o) {
		return my_layers.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return my_layers.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends GIS_layer> c) {
		return my_layers.addAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return my_layers.retainAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return my_layers.removeAll(c);
	}

	@Override
	public void clear() {
		my_layers.clear();
	}

	@Override
	public Meta_data get_Meta_data() {
		return data;
	}


	public String toStringOfGISProject() {
		String final_string = "";
		final_string += "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
				"<kml xmlns=\"http://www.opengis.net/kml/2.2\">\n"
				+ "<Document><Style id=\"red\"><IconStyle><Icon>"
				+ "<href>http://maps.google.com/mapfiles/ms/icons/red-dot.png</href>"
				+ "</Icon></IconStyle>"
				+ "</Style><Style id=\"yellow\"><IconStyle><Icon>"
				+ "<href>http://maps.google.com/mapfiles/ms/icons/yellow-dot.png"
				+ "</href></Icon></IconStyle></Style>"
				+ "<Style id=\"green\"><IconStyle><Icon>"
				+ "<href>http://maps.google.com/mapfiles/ms/icons/green-dot.png</href></Icon>"
				+ "</IconStyle></Style>";
		for(GIS_layer layer: my_layers)
		{
			final_string += ((My_GIS_layer) layer).toStringOfGISLayer();
		}
		final_string += "  </Document> \n </kml>";
		return  final_string;
	}
	

}
