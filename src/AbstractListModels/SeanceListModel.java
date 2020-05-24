package AbstractListModels;

import java.util.List;

import javax.swing.DefaultListModel;

import Seances.Seance;

@SuppressWarnings("rawtypes")
public class SeanceListModel extends DefaultListModel
{
	private static final long serialVersionUID = -7231942121794249902L;
	private final List<Seance> list;

    public SeanceListModel(List<Seance> list) {
        this.list = list;
    }

    @Override
    public int getSize() {
        return list.size();
    }

    @Override
    public Object getElementAt(int index) {
        return list.get(index);
    }
   
}
