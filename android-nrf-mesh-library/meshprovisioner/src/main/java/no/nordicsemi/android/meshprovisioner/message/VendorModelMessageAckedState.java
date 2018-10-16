package no.nordicsemi.android.meshprovisioner.message;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

/**
 * State class for handling VendorModelMessageAckedState messages.
 */
class VendorModelMessageAckedState extends GenericMessageState {

    private static final String TAG = VendorModelMessageAckedState.class.getSimpleName();

    /**
     * Constructs {@link VendorModelMessageAckedState}
     *
     * @param context         Context of the application
     * @param dstAddress      Destination address to which the message must be sent to
     * @param vendorModelMessageAcked Wrapper class {@link VendorModelMessageStatus} containing the opcode and parameters for {@link VendorModelMessageStatus} message
     * @param callbacks       {@link InternalMeshMsgHandlerCallbacks} for internal callbacks
     * @throws IllegalArgumentException exception for invalid arguments
     */
    VendorModelMessageAckedState(@NonNull final Context context,
                                 @NonNull final byte[] dstAddress,
                                 @NonNull final VendorModelMessageAcked vendorModelMessageAcked,
                                 @NonNull final MeshTransport meshTransport,
                                 @NonNull final InternalMeshMsgHandlerCallbacks callbacks) throws IllegalArgumentException {
        super(context, dstAddress, vendorModelMessageAcked, meshTransport, callbacks);
        createAccessMessage();
    }

    @Override
    public MessageState getState() {
        return MessageState.VENDOR_MODEL_ACKNOWLEDGED_STATE;
    }

    /**
     * Creates the access message to be sent to the node
     */
    private void createAccessMessage() {
        final VendorModelMessageAcked vendorModelMessageAcked = (VendorModelMessageAcked) mMeshMessage;
        final byte[] src = vendorModelMessageAcked.getMeshNode().getConfigurationSrc();
        final byte[] key = vendorModelMessageAcked.getAppKey();
        final int akf = vendorModelMessageAcked.getAkf();
        final int aid = vendorModelMessageAcked.getAid();
        final int aszmic = vendorModelMessageAcked.getAszmic();
        final int opCode = vendorModelMessageAcked.getOpCode();
        final byte[] parameters = vendorModelMessageAcked.getParameters();
        final int companyIdentifier = vendorModelMessageAcked.getCompanyIdentifier();
        message = mMeshTransport.createVendorMeshMessage(mNode, companyIdentifier, mSrc, mDstAddress, key, akf, aid, aszmic, opCode, parameters);
        vendorModelMessageAcked.setMessage(message);
        mPayloads.putAll(message.getNetworkPdu());
    }

    @Override
    public void executeSend() {
        Log.v(TAG, "Sending acknowledged vendor model message");
        super.executeSend();
    }
}
