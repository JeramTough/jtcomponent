package test.tree2.channel;


import com.jeramtough.jtcomponent.tree2.adpater.OneTreeNode2Adapter;

/**
 * <pre>
 * Created on 2024/11/21 下午1:43
 * by @author WeiBoWen
 * </pre>
 */
public class ChannelOneTreeNodeAdapter implements OneTreeNode2Adapter<Channel> {

    private Channel channel;


    @Override
    public String getCode() {
        return channel.getCode();
    }

    @Override
    public void setSource(Object source) {
        this.channel = (Channel) source;
    }

    @Override
    public String getKey() {
        return channel.getId().toString();
    }

    @Override
    public String getParentKey() {
        Long parentId = channel.getParentId();
        if (parentId != null && parentId == 0L) {
            return null;
        }
        else {
            if (parentId != null) {
                return parentId.toString();
            }
            else {
                return null;
            }
        }
    }


    @Override
    public Channel getValue() {
        return channel;
    }

    @Override
    public int getOrder() {
        Integer order = channel.getSq();
        if (order == null && channel.getId() != null) {
            order = channel.getId().intValue();
        }
        return order;
    }
}
