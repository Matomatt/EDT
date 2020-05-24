package AbstractListModels;

import java.util.List;

import javax.swing.AbstractListModel;

import Salles.Salle;

@SuppressWarnings("rawtypes")
public class SalleListModel extends AbstractListModel
{
	private static final long serialVersionUID = -3071555162927881355L;
	private final List<Salle> list;

    public SalleListModel(List<Salle> list) {
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