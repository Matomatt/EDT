package AbstractListModels;

import java.util.List;

import javax.swing.AbstractListModel;

import Groupes.Groupe;

@SuppressWarnings("rawtypes")
public class GroupeListModel extends AbstractListModel
{
	private static final long serialVersionUID = -7369204511102333581L;
	private final List<Groupe> list;

    public GroupeListModel(List<Groupe> list) {
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
