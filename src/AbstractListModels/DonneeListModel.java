package AbstractListModels;

import java.util.List;

import javax.swing.AbstractListModel;

import Donnees.Donnee;

@SuppressWarnings("rawtypes")
public class DonneeListModel extends AbstractListModel
{
	private static final long serialVersionUID = 3915965904763100386L;
	private final List<Donnee> list;

    public DonneeListModel(List<Donnee> list) {
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
