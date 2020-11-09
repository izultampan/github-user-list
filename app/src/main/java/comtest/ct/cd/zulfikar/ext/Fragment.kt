package comtest.ct.cd.zulfikar.ext

import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import comtest.ct.cd.zulfikar.R

fun Fragment.createSnackBarNoAction(
    root: View,
    messageStringId: Int,
    snackBarHolderId: Int
): Snackbar =
    createSnackBarNoAction(root, getString(messageStringId), snackBarHolderId)

fun Fragment.createSnackBarNoAction(
    root: View,
    messageText: String,
    snackBarHolderId: Int
): Snackbar = createSnackBar(root, messageText, null, null, null, snackBarHolderId)

fun Fragment.createSnackBar(
    root: View,
    messageStringId: Int,
    actionStringId: Int,
    listener: View.OnClickListener,
    snackBarHolderId: Int
): Snackbar =
    createSnackBar(
        root,
        getString(messageStringId),
        getString(actionStringId),
        listener,
        snackBarHolderId
    )

fun Fragment.createSnackBar(
    root: View,
    messageStringId: Int,
    actionStringId: Int,
    listener: View.OnClickListener,
    snackBarHolderId: Int,
    snackbarDuration: Int
): Snackbar =
    createSnackBar(
        root,
        getString(messageStringId),
        getString(actionStringId),
        listener,
        null,
        snackBarHolderId,
        snackbarDuration
    )

fun Fragment.createSnackBar(
    root: View,
    messageString: String,
    actionStringId: Int,
    listener: View.OnClickListener,
    snackBarHolderId: Int,
    snackbarDuration: Int
): Snackbar =
    createSnackBar(
        root,
        messageString,
        getString(actionStringId),
        listener,
        null,
        snackBarHolderId,
        snackbarDuration
    )

fun Fragment.createSnackBarNoAction(
    root: View,
    messageStringId: Int,
    snackBarHolderId: Int,
    snackbarDuration: Int
): Snackbar =
    createSnackBar(
        root,
        getString(messageStringId),
        null,
        null,
        null,
        snackBarHolderId,
        snackbarDuration
    )

fun Fragment.createSnackBar(
    root: View,
    messageText: String,
    actionText: String?,
    listener: View.OnClickListener?,
    callback: Snackbar.Callback?,
    snackBarHolderId: Int,
    snackbarDuration: Int
): Snackbar {
    val snackBar = Snackbar.make(root, messageText, snackbarDuration)
        .setAction(actionText, listener)
        .apply {
            view.layoutParams = (view.layoutParams as CoordinatorLayout.LayoutParams).apply {
                setMargins(
                    resources.getDimensionPixelSize(R.dimen.space_8dp),
                    0,
                    resources.getDimensionPixelSize(R.dimen.space_8dp),
                    0
                )
                anchorId = snackBarHolderId
                anchorGravity = Gravity.TOP
                gravity = Gravity.TOP
            }
        }

    callback?.let {
        snackBar.addCallback(it)
    }

    val snackBarText = snackBar.view.findViewById<TextView>(R.id.snackbar_text)

    val snackBarAction = snackBar.view.findViewById<TextView>(R.id.snackbar_action)
    snackBarAction.isAllCaps = false

    snackBar.view.background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_snackbar)

    return snackBar
}

fun Fragment.createSnackBar(
    root: View,
    messageText: String,
    actionText: String,
    listener: View.OnClickListener,
    snackBarHolderId: Int
): Snackbar = createSnackBar(root, messageText, actionText, listener, null, snackBarHolderId)

fun Fragment.createSnackBar(
    root: View,
    messageText: String,
    actionText: String?,
    listener: View.OnClickListener?,
    callback: Snackbar.Callback?,
    snackBarHolderId: Int
): Snackbar {
    val snackBar = Snackbar.make(root, messageText, Snackbar.LENGTH_INDEFINITE)
        .setAction(actionText, listener)
        .apply {
            view.layoutParams = (view.layoutParams as CoordinatorLayout.LayoutParams).apply {
                setMargins(
                    resources.getDimensionPixelSize(R.dimen.space_8dp),
                    0,
                    resources.getDimensionPixelSize(R.dimen.space_8dp),
                    0
                )
                anchorId = snackBarHolderId
                anchorGravity = Gravity.TOP
                gravity = Gravity.TOP
            }
        }

    callback?.let {
        snackBar.addCallback(it)
    }

    val snackBarText = snackBar.view.findViewById<TextView>(R.id.snackbar_text)
    val snackBarAction = snackBar.view.findViewById<TextView>(R.id.snackbar_action)
    snackBarAction.isAllCaps = false

    snackBar.view.background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_snackbar)

    return snackBar
}
