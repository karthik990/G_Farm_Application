package p043io.netty.handler.codec.compression;

import androidx.core.app.FrameMetricsAggregator;
import androidx.core.view.InputDeviceCompat;
import com.google.android.exoplayer2.extractor.p040ts.PsExtractor;
import com.google.firebase.database.core.ValidationPath;
import com.google.logging.type.LogSeverity;
import com.mobiroller.activities.user.UserLoginActivity;
import org.apache.http.HttpStatus;
import org.objectweb.asm.Opcodes;

/* renamed from: io.netty.handler.codec.compression.Bzip2Rand */
final class Bzip2Rand {
    private static final int[] RNUMS = {619, 720, Opcodes.LAND, 481, 931, 816, 813, 233, 566, 247, 985, 724, HttpStatus.SC_RESET_CONTENT, 454, 863, 491, 741, 242, 949, 214, 733, 859, 335, 708, 621, 574, 73, 654, 730, 472, HttpStatus.SC_INSUFFICIENT_SPACE_ON_RESOURCE, 436, 278, 496, 867, 210, 399, 680, 480, 51, 878, 465, 811, Opcodes.RET, 869, 675, 611, 697, 867, 561, 862, 687, HttpStatus.SC_INSUFFICIENT_STORAGE, 283, 482, 129, 807, 591, 733, 623, Opcodes.FCMPG, 238, 59, 379, 684, 877, 625, Opcodes.RET, 643, 105, Opcodes.TABLESWITCH, 607, 520, 932, 727, 476, 693, 425, Opcodes.FRETURN, 647, 73, 122, 335, 530, 442, 853, 695, 249, 445, 515, 909, 545, 703, 919, 874, 474, 882, 500, 594, 612, 641, 801, 220, Opcodes.IF_ICMPGE, 819, 984, 589, InputDeviceCompat.SOURCE_DPAD, 495, 799, Opcodes.IF_ICMPLT, 604, 958, 533, 221, 400, 386, 867, 600, 782, 382, 596, HttpStatus.SC_REQUEST_URI_TOO_LONG, Opcodes.LOOKUPSWITCH, 516, 375, 682, 485, 911, 276, 98, 553, Opcodes.IF_ICMPGT, 354, 666, 933, HttpStatus.SC_FAILED_DEPENDENCY, 341, 533, 870, 227, 730, 475, Opcodes.INVOKEDYNAMIC, 263, 647, 537, 686, 600, 224, 469, 68, 770, 919, Opcodes.ARRAYLENGTH, 373, 294, 822, 808, HttpStatus.SC_PARTIAL_CONTENT, Opcodes.INVOKESTATIC, 943, 795, 384, 383, 461, 404, 758, 839, 887, 715, 67, 618, 276, 204, 918, 873, 777, 604, 560, 951, Opcodes.IF_ICMPNE, 578, 722, 79, 804, 96, 409, 713, 940, 652, 934, 970, 447, 318, 353, 859, 672, 112, 785, UserLoginActivity.USER_LOGIN_REQUEST, 863, 803, 350, 139, 93, 354, 99, 820, 908, 609, 772, Opcodes.IFNE, 274, 580, Opcodes.INVOKESTATIC, 79, 626, 630, 742, 653, 282, 762, 623, 680, 81, 927, 626, 789, Opcodes.LUSHR, HttpStatus.SC_LENGTH_REQUIRED, 521, 938, 300, 821, 78, 343, Opcodes.DRETURN, 128, 250, Opcodes.TABLESWITCH, 774, 972, 275, 999, 639, 495, 78, 352, Opcodes.IAND, 857, 956, 358, 619, 580, 124, 737, 594, 701, 612, 669, 112, 134, 694, 363, 992, 809, 743, Opcodes.JSR, 974, 944, 375, 748, 52, 600, 747, UserLoginActivity.REGISTER_FIRST, Opcodes.INVOKEVIRTUAL, 862, 81, 344, 805, 988, 739, FrameMetricsAggregator.EVERY_DURATION, 655, 814, 334, 249, 515, 897, 955, 664, 981, 649, 113, 974, 459, 893, 228, 433, 837, 553, 268, 926, PsExtractor.VIDEO_STREAM_MASK, 102, 654, 459, 51, 686, 754, 806, 760, 493, 403, HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE, 394, 687, LogSeverity.ALERT_VALUE, 946, 670, 656, 610, 738, 392, 760, 799, 887, 653, 978, 321, 576, 617, 626, 502, 894, 679, 243, 440, 680, 879, Opcodes.MONITORENTER, 572, 640, 724, 926, 56, 204, LogSeverity.ALERT_VALUE, 707, Opcodes.DCMPL, 457, 449, 797, Opcodes.MONITOREXIT, 791, 558, 945, 679, 297, 59, 87, 824, 713, 663, 412, 693, 342, 606, 134, 108, 571, 364, 631, 212, Opcodes.FRETURN, 643, 304, 329, 343, 97, 430, 751, 497, 314, 983, 374, 822, 928, 140, HttpStatus.SC_PARTIAL_CONTENT, 73, 263, 980, 736, 876, 478, 430, HttpStatus.SC_USE_PROXY, Opcodes.TABLESWITCH, 514, 364, 692, 829, 82, 855, 953, 676, 246, 369, 970, 294, 750, 807, 827, Opcodes.FCMPG, 790, 288, 923, 804, 378, 215, 828, 592, 281, 565, 555, 710, 82, 896, 831, 547, 261, 524, 462, 293, 465, 502, 56, 661, 821, 976, 991, 658, 869, 905, 758, 745, Opcodes.INSTANCEOF, ValidationPath.MAX_PATH_LENGTH_BYTES, 550, 608, 933, 378, 286, 215, 979, 792, 961, 61, 688, 793, 644, 986, 403, 106, 366, 905, 644, 372, 567, 466, 434, UserLoginActivity.USER_LOGIN_REQUEST, 210, 389, 550, 919, 135, 780, 773, 635, 389, 707, 100, 626, 958, Opcodes.IF_ACMPEQ, HttpStatus.SC_GATEWAY_TIMEOUT, 920, Opcodes.ARETURN, Opcodes.INSTANCEOF, 713, 857, 265, 203, 50, 668, 108, UserLoginActivity.USER_LOGIN_REQUEST, 990, 626, Opcodes.MULTIANEWARRAY, 510, 357, 358, 850, 858, 364, 936, 638};

    static int rNums(int i) {
        return RNUMS[i];
    }

    private Bzip2Rand() {
    }
}
