package clawer.util;

import clawer.model.Phone;

import java.util.ArrayList;
import java.util.List;

/**
 * Date 2021/4/18
 * @author 0mega_0
 *一个单例化List<Phone>的类，用于获取同一个phoeList对象，方便最后输出
 */
public class PipeOutputFactor {
    private List<Phone> phoneList = new ArrayList<>();
    private static class ClassInstance{
        private static PipeOutputFactor instance=new PipeOutputFactor();
    }

    private PipeOutputFactor(){
    }

    public static PipeOutputFactor getInstance(){
        return ClassInstance.instance;
    }
    public List getPhoneList(){
        return this.phoneList;
    }

}
